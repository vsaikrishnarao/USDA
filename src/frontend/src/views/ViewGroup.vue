<template>
  <div class="view-groups">
    <h2 class="heading">Animal Groups</h2>

    <!-- Search -->
    <div class="search-bar">
      <input
          type="text"
          v-model="searchQuery"
          placeholder="Search by Short Name"
      />
      <button class="clear" @click="clearSearch">Clear</button>
    </div>

    <!-- Table -->
    <div class="table-wrapper">
    <table class="animal-table">
      <thead>
      <tr>
        <th><input type="checkbox" v-model="selectAll" @change="toggleSelectAll" /></th>
        <th>ID</th>
        <th>Short Name</th>
        <th>Long Name</th>
        <th>Active</th>
        <th>Date Valid</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="group in displayedGroups" :key="group.id">
        <td><input type="checkbox" v-model="selectedGroups" :value="group" /></td>
        <td>{{ group.id }}</td>
        <td>{{ group.nameGrpShort }}</td>
        <td>{{ group.nameGrpLong }}</td>
        <td>{{ group.active ? 'Yes' : 'No' }}</td>
        <td>{{ group.dateValid }}</td>
      </tr>
      </tbody>
    </table>
    </div>

    <!-- Footer Buttons -->
    <div class="footer-buttons">
      <button class="btn add-btn" @click="openForm('add')">Add</button>
      <button class="btn edit-btn" @click="openForm('edit')">Edit</button>
    </div>

    <!-- Modal Popup -->
    <div v-if="showFormModal" class="modal-overlay">
      <div class="modal-content">
        <GroupForm
            :mode="formMode"
            :existingGroup="formMode === 'edit' ? selectedGroups[0] : null"
            :nextGroupId="formMode === 'add' ? nextGroupId : null"
            @close="closeForm"
            @saved="handleSaved"
        />
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import GroupForm from "@/views/GroupForm.vue";

export default {
  name: "ViewGroup",
  components: { GroupForm },
  data() {
    return {
      groups: [],
      searchQuery: "",
      selectedGroups: [],
      selectAll: false,
      showFormModal: false,
      formMode: "add", // 'add' or 'edit'
      nextGroupId: null,
    };
  },
  computed: {
    displayedGroups() {
      if (this.searchQuery.trim() !== "") {
        return this.groups.filter(group =>
            group.nameGrpShort.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
      }
      return this.groups;
    }
  },
  created() {
    this.fetchGroups();
  },
  methods: {
    fetchGroups() {
      axios.get(`${process.env.VUE_APP_API_BASE_URL}/groups/all`)
          .then(response => {
            this.groups = response.data;
            this.calculateNextId();
          })
          .catch(error => {
            console.error("Error fetching groups:", error);
          });
    },
    calculateNextId() {
      if (this.groups.length > 0) {
        this.nextGroupId = Math.max(...this.groups.map(g => g.id)) + 1;
      } else {
        this.nextGroupId = 1; // First group
      }
    },
    toggleSelectAll() {
      if (this.selectAll) {
        this.selectedGroups = [...this.groups];
      } else {
        this.selectedGroups = [];
      }
    },
    clearSearch() {
      this.searchQuery = "";
    },
    openForm(mode) {
      if (mode === "edit" && this.selectedGroups.length !== 1) {
        alert("Please select exactly one group to edit.");
        return;
      }
      this.formMode = mode;
      this.showFormModal = true;
    },
    closeForm() {
      this.showFormModal = false;
    },
    handleSaved() {
      this.fetchGroups();
      this.selectedGroups = [];
      this.selectAll = false;
      this.closeForm();
    }
  }
};
</script>

<style scoped>
.table-wrapper {
  max-height: 450px; /* or 500px depending on screen size */
  overflow-y: auto;
  margin-bottom: 20px; /* So buttons won't stick to table */
}

.view-groups {
  padding: 20px;
  background: url('@/assets/static.png') no-repeat center center;
  background-size: cover;
  min-height: 100vh;
}

.heading {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
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

.clear {
  padding: 8px 12px;
  background-color: crimson;
  color: white;
  font-size: 14px;
  margin-left: 8px;
  border-radius: 4px;
}

.animal-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
  background-color: #f3f0e3;
  opacity: 0.95;
}

.animal-table th,
.animal-table td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: center;
}

.animal-table th {
  background-color: #f0f0f0;
}

.footer-buttons {
  text-align: center;
  margin-top: 20px;
}

.btn {
  padding: 10px 20px;
  font-size: 15px;
  border: none;
  cursor: pointer;
  margin: 0 10px;
  border-radius: 4px;
}

.add-btn {
  background-color: navy;
  color: white;
}

.edit-btn {
  background-color: darkred;
  color: white;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: white;
  padding: 30px;
  border-radius: 10px;
  min-width: 400px;
}
</style>
