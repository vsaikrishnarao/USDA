<template>
  <div class="view-groups">
    <button class="close-btn" @click="goBack">X</button>

    <div class="table-container">
      <h2>Assigned Animal Groups</h2>
      <table class="group-table">
        <thead>
        <tr>
          <th>
            <input
                type="checkbox"
                :checked="selectAll"
                @change="toggleAllRows"
            />
          </th>
          <th>Group Name</th>
          <th>Head</th>
          <th>AUE</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="group in groupRows" :key="group.id">
          <td>
            <input type="checkbox" v-model="group.selected"/>
          </td>
          <td>{{ group.nameGrpShort }}</td>
          <td>{{ group.head }}</td>
          <td>{{ group.aueGrp }}</td>
          <td>
<!--            <button class="view-btn">View Animals</button>-->
            <button class="view-btn" @click="goToAnimals(group.pairList, group.agid)">View Animals</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

export default {
  name: "ViewGroups",
  setup() {
    const router = useRouter();
    const groupRows = ref([]);
    const selectAll = ref(false); // tracks if the user explicitly toggled Select All

    const goBack = () => {
      router.push("/home");
    };

    const toggleAllRows = () => {
      selectAll.value = !selectAll.value;
      groupRows.value.forEach(group => {
        group.selected = selectAll.value;
      });
    };

    const viewAnimals = (groupId) => {
      router.push(`/viewanimals/${groupId}`);
    };

    const goToAnimals = (pairs,groupId) => {
      sessionStorage.setItem("animalClassPairs", JSON.stringify(pairs));
      sessionStorage.setItem("currentGroupId", groupId); // 👈 add this
      router.push("/viewanimals");
    };

    const fetchAnimalNamedData = async () => {
      try {
        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/animal/named-data`);
        sessionStorage.setItem("animalNamedData", JSON.stringify(response.data));
      } catch (err) {
        console.error("Failed to fetch animal named-data:", err);
      }
    };

    onMounted(async () => {
      const stored = sessionStorage.getItem("selectedGroupIds");
      if (!stored) return;
      const ids = JSON.parse(stored);
      if (!ids.length) return;

      try {
        // Step 1: Fetch and cache named animal data
        await fetchAnimalNamedData();

        const res = await axios.post(
            `${process.env.VUE_APP_API_BASE_URL}/group/assignments/by-ids`,
            ids
        );

        const groupMapRes = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/groups/all`);

        const groupMap = Object.fromEntries(
            groupMapRes.data.map((g) => [g.id, g.nameGrpShort])
        );
        sessionStorage.setItem("groupList", JSON.stringify(groupMapRes.data));


        groupRows.value = res.data.map((g) => ({
          ...g,
          nameGrpShort: groupMap[g.agid] || "N/A",
          pairList: g.animalClassPairs // ✅ use this field from the backend
        }));

      } catch (err) {
        console.error("Error loading group data:", err);
      }
    });

    return {
      groupRows,
      selectAll,
      goBack,
      toggleAllRows,
      viewAnimals,
      goToAnimals
    };
  }
};
</script>


<style scoped>
.view-groups {
  position: relative;
  width: 100%;
  height: 100vh;
  background-image: url("@/assets/static.png");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 30px;
}

/* Close button stays at top right */
.close-btn {
  position: absolute;
  top: 15px;
  right: 25px;
  background-color: red;
  color: white;
  border: none;
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 50%;
  z-index: 1000;
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.close-btn:hover {
  background-color: darkred;
}

/* Table container centered */
.table-container {
  max-width: 900px;
  width: 100%;
  background-color: rgba(255, 255, 255, 0.92);
  padding: 20px;
  border-radius: 8px;
  box-shadow: 1px 2px 8px rgba(0, 0, 0, 0.2);
  margin-top: 30px;
  overflow-x: auto;
}

/* Heading centered */
.table-container h2 {
  text-align: center;
  font-family: 'Times New Roman', serif;
  margin-bottom: 16px;
}

/* Table styling */
.group-table {
  width: 100%;
  border-collapse: collapse;
  font-family: 'Times New Roman', serif;
  font-size: 15px;
  background-color: white;
}

.group-table th,
.group-table td {
  border: 1px solid #ccc;
  padding: 8px 10px;
  text-align: center;
}

.group-table th {
  background-color: #f0f0f0;
  font-weight: bold;
}

.group-table tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

/* Static View button */
.view-btn {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 4px 10px;
  font-size: 14px;
  border-radius: 4px;
  cursor: pointer;
}
.view-btn:hover {
  background-color: #0056b3;
}
</style>
