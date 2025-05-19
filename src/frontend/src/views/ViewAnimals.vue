<template>
  <div class="view-animals">
    <button class="close-btn" @click="goBack">X</button>

    <div class="table-container">
<!--      <h2>Animals in Selected Group</h2>-->
      <h2>Animals in {{currentGroupName}}</h2>

      <!-- ✅ Search Bar -->
      <div class="search-bar">
        <input
            type="text"
            v-model="searchQuery"
            placeholder="Search by label..."
        />
      </div>

      <table class="group-table">
        <thead>
        <tr>
          <th><input type="checkbox" v-model="selectAll" @change="toggleAll" /></th>
          <th>Label</th>
          <th>Name Type</th>
          <th>DOB</th>
          <th>Sex</th>
          <th>Intact</th>
          <th>Owner</th>
          <th>Class ID</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="animal in displayedAnimals" :key="animal.aniId">
          <td><input type="checkbox" v-model="selected" :value="animal.aniId" /></td>
          <td>{{ animal.label }}</td>
          <td>{{ animal.nameType }}</td>
          <td>{{ animal.dob }}</td>
          <td>{{ animal.sex }}</td>
          <td>{{ animal.intact ? "Yes" : "No" }}</td>
          <td>{{ animal.owner }}</td>
          <td>{{ animal.clsId }}</td>
        </tr>
        </tbody>
      </table>

      <!-- ✅ Move To Dropdown -->
      <div class="move-to-dropdown">
        <label for="move-select">Move to:</label>
        <select id="move-select" v-model="moveToGroupId">
          <option disabled value="">Select group</option>
          <option v-for="g in groupNameOptions" :key="g.id" :value="g.id">
            {{ g.name }}
          </option>
        </select>

        <button class="move-btn" @click="moveSelectedAnimals" :disabled="!selected.length || !moveToGroupId">
          Move
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { ref, onMounted, computed } from "vue";
import axios from "axios";

export default {
  name: "ViewAnimals",
  setup() {
    const router = useRouter();
    const animals = ref([]);
    const selected = ref([]);
    const selectAll = ref(false);
    const searchQuery = ref("");
    const moveToGroupId = ref("");
    const groupList = ref([]);
    const currentGroupId = ref(null);
    const currentGroupName = ref("");

    const groupNameOptions = computed(() => {
      return groupList.value
          .filter(g => g.id !== currentGroupId.value)
          .map(g => ({ id: g.id, name: g.nameGrpShort }));
    });

    const goBack = () => {
      router.push("/viewgroups");
    };

    const toggleAll = () => {
      if (selectAll.value) {
        selected.value = animals.value.map((a) => a.aniId);
      } else {
        selected.value = [];
      }
    };

    const displayedAnimals = computed(() => {
      if (!searchQuery.value.trim()) return animals.value;
      return animals.value.filter((animal) =>
          animal.label?.toLowerCase().includes(searchQuery.value.toLowerCase())
      );
    });

    const loadAssignmentData = () => {
      try {
        const storedPairs = sessionStorage.getItem("animalClassPairs");
        const storedNamedData = sessionStorage.getItem("animalNamedData");
        const storedGroupList = sessionStorage.getItem("groupList");
        currentGroupId.value = Number(sessionStorage.getItem("currentGroupId"));

        if (!storedPairs || !storedNamedData) throw new Error("Missing session data");

        const pairs = JSON.parse(storedPairs);
        const namedData = JSON.parse(storedNamedData);
        if (storedGroupList) groupList.value = JSON.parse(storedGroupList);

        const namedMap = Object.fromEntries(namedData.map((a) => [a.aniId, a]));

        animals.value = pairs.map(([aniId, clsId]) => {
          const match = namedMap[aniId] || {};
          return {
            aniId,
            clsId,
            label: match.label || "N/A",
            nameType: match.nameType || "N/A",
            dob: match.dob || "N/A",
            sex: match.sex || "N/A",
            intact: match.intact ?? false,
            owner: match.owner || "N/A"
          };
        });

        const groupMatch = groupList.value.find(g => g.id === currentGroupId.value);
        currentGroupName.value = groupMatch ? groupMatch.nameGrpShort : "Selected Group";

        selected.value = [];
        selectAll.value = false;
      } catch (err) {
        console.warn("Redirecting due to error:", err.message);
        router.push("/viewgroups");
      }
    };

    // const moveSelectedAnimals = async () => {
    //   if (!selected.value.length || !moveToGroupId.value) return;
    //
    //   const payload = {
    //     sg: groupList.value.find(g => g.id === currentGroupId.value)?.nameGrpShort,
    //     tg: groupList.value.find(g => g.id === moveToGroupId.value)?.nameGrpShort,
    //     animalIds: selected.value
    //   };
    //
    //   try {
    //     await axios.post("http://localhost:8095/api/group/move-animals", payload);
    //     alert("Animals moved successfully.");
    //     loadAssignmentData(); // ✅ reload updated data
    //   } catch (err) {
    //     alert("Failed to move animals: " + err.message);
    //   }
    // };

    const moveSelectedAnimals = async () => {
      if (!selected.value.length || !moveToGroupId.value) return;

      const payload = {
        sg: groupList.value.find(g => g.id === currentGroupId.value)?.nameGrpShort,
        tg: groupList.value.find(g => g.id === moveToGroupId.value)?.nameGrpShort,
        animalIds: selected.value
      };

      try {
        // Step 1: Move animals
        await axios.post(`${process.env.VUE_APP_API_BASE_URL}/group/move-animals`, payload);

        // ✅ Step 2: Get updated assignment for current group
        const updatedAssignments = await axios.post(
            `${process.env.VUE_APP_API_BASE_URL}/group/assignments/by-ids`,
            [currentGroupId.value]
        );

        const current = updatedAssignments.data.find(a => a.agid === currentGroupId.value);

        // ✅ Step 3: Update sessionStorage with latest animal list
        sessionStorage.setItem("animalClassPairs", JSON.stringify(current?.animalClassPairs || []));

        // Step 4: Refresh table
        loadAssignmentData();

        alert("Animals moved successfully.");
      } catch (err) {
        alert("Failed to move animals: " + err.message);
      }
    };


    onMounted(() => {
      loadAssignmentData();
    });

    return {
      animals,
      selected,
      selectAll,
      toggleAll,
      goBack,
      searchQuery,
      displayedAnimals,
      moveToGroupId,
      groupNameOptions,
      moveSelectedAnimals,
      currentGroupName
    };
  }
};
</script>

<style scoped>
.view-animals {
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

.search-bar {
  margin-bottom: 15px;
  text-align: center;
}

.search-bar input {
  padding: 8px;
  font-size: 14px;
  width: 200px;
}

.table-container h2 {
  text-align: center;
  font-family: 'Times New Roman', serif;
  margin-bottom: 16px;
}

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

.move-to-dropdown {
  margin-top: 20px;
  text-align: center;
}

.move-to-dropdown select {
  padding: 8px;
  font-size: 14px;
  min-width: 160px;
}

.move-btn {
  margin-left: 10px;
  padding: 8px 14px;
  font-size: 14px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.move-btn:disabled {
  background-color: #999;
  cursor: not-allowed;
}
.move-btn:hover {
  background-color: #0056b3;
}
</style>
