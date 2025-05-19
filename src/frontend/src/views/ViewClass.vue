<template>
  <div class="view-classes">
    <h2 class="heading">Animal Classes</h2>

    <!-- Search -->
    <div class="search-bar">
      <input
          type="text"
          v-model="searchQuery"
          placeholder="Search by Label"
      />
      <button class="clear" @click="clearSearch">Clear</button>
    </div>

    <!-- Table -->
    <!-- 📦 Wrap the table inside a new scrollable wrapper -->
    <div class="table-wrapper">
      <table class="animal-table">
        <thead>
        <tr>
          <th><input type="checkbox" v-model="selectAll" @change="toggleSelectAll" /></th>
          <th>ID</th>
          <th>Label</th>
          <th>AUE</th>
          <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="cls in displayedClasses" :key="cls.id">
          <td><input type="checkbox" v-model="selectedClasses" :value="cls" /></td>
          <td>{{ cls.id }}</td>
          <td>{{ cls.label }}</td>
          <td>{{ cls.aue }}</td>
          <td>{{ cls.description }}</td>
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
        <ClassForm
            :mode="formMode"
            :existingClass="formMode === 'edit' ? selectedClasses[0] : null"
            :nextClassId="formMode === 'add' ? nextClassId : null"
            @close="closeForm"
            @saved="handleSaved"
        />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import ClassForm from "@/views/ClassForm.vue";

export default {
  name: "ViewClass",
  components: { ClassForm },
  data() {
    return {
      classes: [],
      searchQuery: "",
      selectedClasses: [],
      selectAll: false,
      showFormModal: false,
      formMode: "add",
      nextClassId: null
    };
  },
  computed: {
    displayedClasses() {
      if (this.searchQuery.trim() !== "") {
        return this.classes.filter(cls =>
            cls.label.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
      }
      return this.classes;
    }
  },
  created() {
    this.fetchClasses();
  },
  methods: {
    fetchClasses() {
      axios.get(`${process.env.VUE_APP_API_BASE_URL}/classes/all`)
          .then(response => {
            this.classes = response.data;
            this.calculateNextId();
          })
          .catch(error => {
            console.error("Error fetching classes:", error);
          });
    },
    calculateNextId() {
      if (this.classes.length > 0) {
        this.nextClassId = Math.max(...this.classes.map(c => c.id)) + 1;
      } else {
        this.nextClassId = 1;
      }
    },
    toggleSelectAll() {
      if (this.selectAll) {
        this.selectedClasses = [...this.classes];
      } else {
        this.selectedClasses = [];
      }
    },
    clearSearch() {
      this.searchQuery = "";
    },
    openForm(mode) {
      if (mode === "edit" && this.selectedClasses.length !== 1) {
        alert("Please select exactly one class to edit.");
        return;
      }
      this.formMode = mode;
      this.showFormModal = true;
    },
    closeForm() {
      this.showFormModal = false;
    },
    handleSaved() {
      this.fetchClasses();
      this.selectedClasses = [];
      this.selectAll = false;
      this.closeForm();
    }
  }
};
</script>

<style scoped>
/* Same styles as ViewGroup.vue */
.table-wrapper {
  max-height: 450px; /* or 500px depending on screen size */
  overflow-y: auto;
  margin-bottom: 20px; /* So buttons won't stick to table */
}

.view-classes {
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
