<template>
  <div class="map-wrapper">
    <!-- Static Image with Overlay Text -->
    <transition name="fade">
      <div v-if="!isMapVisible" @click="toggleMap" class="image-container">
        <img src="@/assets/static.png" alt="Static Map" />
        <div class="image-overlay-text">
          Click anywhere on the image to view the interactive map
        </div>
      </div>
    </transition>

    <!-- Interactive Leaflet Map -->
    <transition name="fade">
      <div v-if="isMapVisible" class="map-container">
        <button @click.stop="closeMap" class="close-btn">X</button>
        <div id="map" ref="mapContainer"></div>
      </div>
    </transition>
  </div>
</template>

<script>
import { ref, watch, nextTick, onMounted } from "vue";
import { useRouter } from "vue-router";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import axios from "axios";
import proj4 from "proj4";

// Define projection transformations
proj4.defs("EPSG:32614", "+proj=utm +zone=14 +datum=WGS84 +units=m +no_defs");
proj4.defs("EPSG:4326", "+proj=longlat +datum=WGS84 +no_defs");

export default {
  name: "MapView",
  setup() {
    const router = useRouter();
    const mapContainer = ref(null);
    const isMapVisible = ref(false);
    let map = null;
    const polygons = ref([]);
    const groupMap = ref({}); // group name → ID map

    const fetchAllGroups = async () => {
      try {
        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/groups/all`);
        groupMap.value = Object.fromEntries(response.data.map(g => [g.nameGrpShort, g.id]));
      } catch (err) {
        console.error("Failed to fetch group mapping:", err);
      }
    };

    const toggleMap = () => {
      isMapVisible.value = !isMapVisible.value;
    };

    const closeMap = () => {
      const btn = document.querySelector(".close-btn");
      if (btn) {
        btn.classList.add("fade-out");
        setTimeout(() => {
          btn.style.display = "none";
          isMapVisible.value = false;
        }, 300);
      } else {
        isMapVisible.value = false;
      }
    };

    watch(isMapVisible, async (visible) => {
      if (visible) {
        await nextTick();
        initMap();
      }
    });

    onMounted(async () => {
      await fetchAllGroups();

      window.addEventListener("navigate-to-viewgroups", (event) => {
        const groupNames = event.detail?.groupNames;

        const cleanGroupNames = Array.isArray(groupNames)
            ? groupNames
            : String(groupNames || "")
                .replace(/[{}"]/g, "")
                .split(",")
                .map(name => name.trim())
                .filter(name => name !== "");

        const groupIds = cleanGroupNames
            .map(name => groupMap.value[name])
            .filter(id => id != null);

        sessionStorage.setItem("selectedGroupIds", JSON.stringify(groupIds));
        router.push("/viewgroups");
      });
    });

    const initMap = async () => {
      if (!mapContainer.value) return;
      if (map) {
        map.remove();
        map = null;
      }

      map = L.map(mapContainer.value).setView([30, -97], 6);
      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: "&copy; OpenStreetMap contributors",
      }).addTo(map);

      try {
        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/lmu/named-view`);

        response.data.forEach((lmu) => {
          if (!lmu.geom) return;

          let geojson;
          try {
            geojson = JSON.parse(lmu.geom);
          } catch (error) {
            console.error("Error parsing geom for LMU", error);
            return;
          }

          if (!geojson.coordinates || geojson.coordinates.length === 0) return;

          const convertedCoordinates = geojson.coordinates[0]
              .map(utmPoint => {
                if (!utmPoint || utmPoint.length < 2) return null;
                const [lon, lat] = proj4("EPSG:32614", "EPSG:4326", [utmPoint[0], utmPoint[1]]);
                return [lat, lon];
              })
              .filter(coord => coord !== null);

          if (convertedCoordinates.length === 0) return;

          const clsArray = Array.isArray(lmu.classLabelList)
              ? lmu.classLabelList
              : (lmu.classLabelList || "")
                  .toString().replace(/[{}"]/g, "").split(",").filter(v => v !== "");

          const clsCount = clsArray.reduce((acc, label) => {
            acc[label] = (acc[label] || 0) + 1;
            return acc;
          }, {});
          const clsSummary = Object.entries(clsCount).map(([cls, cnt]) => `Class ${cls}: ${cnt}`).join("<br>");
          const totalHead = clsArray.length;

          const groupList = Array.isArray(lmu.nameGrpShortList)
              ? lmu.nameGrpShortList
              : (lmu.nameGrpShortList || "")
                  .toString().replace(/[{}"]/g, "").split(",").filter(v => v !== "").join("<br>");

          let color = "#2a623d", fillColor = "#4caf50";
          if (lmu.type?.toLowerCase() === "corral") {
            color = "#0000ff"; fillColor = "#87cefa";
          } else if (lmu.type?.toLowerCase() === "alley") {
            color = "#808080"; fillColor = "#c0c0c0";
          }

          const polygon = L.polygon(convertedCoordinates, {
            color,
            fillColor,
            fillOpacity: 0.7,
            weight: 2,
          }).addTo(map);

          polygon.bindPopup(`
            <div class="popup-content">
              <h3>${lmu.label || "LMU"}</h3>
              <p><strong>Type:</strong> ${lmu.type || "N/A"}</p>
              <p><strong>Area (ha):</strong> ${lmu.areaHa || "N/A"}</p>
              <p><strong>Current Grazing use:</strong> ${lmu.audGrz || "N/A"}</p>
              <p><strong>Forage:</strong> ${lmu.forage || "N/A"}</p>
              <p><strong>Number of days to reach Target use:</strong> ${parseInt(lmu.noOfDays) || "N/A"}</p>
              <p><strong>Assigned Animal Groups:</strong><br> ${groupList || "N/A"}</p>
              <p><strong>Total number of animals:</strong> ${totalHead || 0}</p>
              <p><strong>Total AUE:</strong> ${lmu.totalAueGrp || "N/A"}</p>
              <p><strong>Animal Class Breakdown:</strong><br>${clsSummary || "N/A"}</p>
              <div style="margin-top: 10px; text-align: center;">
                <button class="view-groups-btn">View Groups</button>
              </div>
            </div>
          `);

          // ✅ Handle click event correctly
          polygon.on("popupopen", () => {
            const btn = document.querySelector(".view-groups-btn");
            if (btn) {
              btn.onclick = () => {
                const groupNamesRaw = lmu.nameGrpShortList;
                const groupNames = Array.isArray(groupNamesRaw)
                    ? groupNamesRaw
                    : String(groupNamesRaw || "")
                        .replace(/[{}"]/g, "")
                        .split(",")
                        .map(name => name.trim())
                        .filter(name => name !== "");

                window.dispatchEvent(new CustomEvent("navigate-to-viewgroups", {
                  detail: { groupNames }
                }));
              };
            }
          });

          polygons.value.push(polygon);
        });

        if (polygons.value.length > 0) {
          map.fitBounds(polygons.value.map(p => p.getBounds()));
        }
      } catch (error) {
        console.error("Error fetching LMU data:", error);
      }
    };

    return {
      mapContainer,
      isMapVisible,
      toggleMap,
      closeMap
    };
  }
};
</script>

<style scoped>
.map-wrapper {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
}

/* Static image */
.image-container {
  position: relative;
  width: 100%;
  height: 100%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* Overlay text */
.image-overlay-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.6);
  color: white;
  padding: 12px 24px;
  border-radius: 8px;
  font-size: 18px;
  font-weight: 500;
  text-align: center;
  z-index: 10;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.3);
}

/* Leaflet map */
.map-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

#map {
  width: 100%;
  height: 100%;
}

/* Close button */
.close-btn {
  position: absolute;
  top: 15px;
  right: 25px;
  background-color: red;
  color: white;
  border: none;
  padding: 12px;
  font-size: 18px;
  cursor: pointer;
  opacity: 1;
  border-radius: 50%;
  z-index: 1000;
  width: 40px;
  height: 40px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease-in-out;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn:hover {
  transform: scale(1.1);
  background-color: darkred;
}

.close-btn.fade-out {
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
}

/* Button inside popup */
.view-groups-btn {
  background-color: #007bff;
  color: white;
  padding: 8px 16px;
  font-size: 14px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.view-groups-btn:hover {
  background-color: #0056b3;
}

/* Transitions */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>