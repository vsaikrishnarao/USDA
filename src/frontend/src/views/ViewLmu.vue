// ViewLmu.vue
<template>
  <div class="view-lmus">
    <h2 class="heading">Land Management Units (LMU)</h2>

    <!-- Search -->
    <div class="search-bar">
      <input type="text" v-model="searchQuery" placeholder="Search by Label" />
      <button class="clear" @click="clearSearch">Clear</button>
    </div>

    <!-- Table -->
    <div class="table-wrapper">
      <table class="animal-table">
        <thead>
        <tr>
          <th><input type="checkbox" v-model="selectAll" @change="toggleSelectAll" /></th>
          <th>GID</th>
          <th>Label</th>
          <th>Type</th>
          <th>Grzbl Factor</th>
          <th>Area (ha)</th>
          <th>Date Valid</th> <!-- ✅ added here -->
        </tr>
        </thead>
        <tbody>
        <tr v-for="lmu in displayedLmus" :key="lmu.gid">
          <td><input type="checkbox" v-model="selectedLmus" :value="lmu" /></td>
          <td>{{ lmu.gid }}</td>
          <td>{{ lmu.label }}</td>
          <td>{{ lmu.type }}</td>
          <td>{{ lmu.grzblFactor }}</td>
          <td>{{ lmu.areaHectares }}</td>
          <td>{{ lmu.dateValid?.value || '' }}</td> <!-- ✅ use optional chaining for safety -->
        </tr>
        </tbody>
      </table>
    </div>

    <!-- Footer Buttons -->
    <div class="footer-buttons">
      <button class="btn edit-btn" @click="openForm('edit')">Edit</button>
    </div>

    <!-- Modal Popup -->
    <div v-if="showFormModal" class="modal-overlay">
      <div class="modal-content">
        <LmuForm
            :mode="formMode"
            :existingLmu="selectedLmus.length === 1 ? selectedLmus[0] : null"
            @close="closeForm"
            @saved="handleSaved"
        />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import LmuForm from "@/views/LmuForm.vue";

export default {
  name: "ViewLmu",
  components: { LmuForm },
  data() {
    return {
      lmus: [],
      searchQuery: "",
      selectedLmus: [],
      selectAll: false,
      showFormModal: false,
      formMode: "edit"
    };
  },
  computed: {
    displayedLmus() {
      if (this.searchQuery.trim() !== "") {
        return this.lmus.filter(lmu => lmu.label.toLowerCase().includes(this.searchQuery.toLowerCase()));
      }
      return this.lmus;
    }
  },
  created() {
    this.fetchLmus();
  },
  methods: {
    fetchLmus() {
      axios.get(`${process.env.VUE_APP_API_BASE_URL}/lmu/all`)
          .then(response => {
            this.lmus = response.data;
          })
          .catch(error => console.error("Error fetching LMUs:", error));
    },
    toggleSelectAll() {
      this.selectAll = !this.selectAll;
      this.selectedLmus = this.selectAll ? [...this.lmus] : [];
    },
    clearSearch() {
      this.searchQuery = "";
    },
    openForm(mode) {
      if (this.selectedLmus.length !== 1) {
        alert("Please select exactly one LMU to edit.");
        return;
      }
      this.formMode = mode;
      this.showFormModal = true;
    },
    closeForm() {
      this.showFormModal = false;
    },
    handleSaved() {
      this.fetchLmus();
      this.selectedLmus = [];
      this.selectAll = false;
      this.closeForm();
    }
  }
};
</script>


<style scoped>
  .view-lmus {
    padding: 20px;
    background: url('@/assets/static.png') no-repeat center center;
    background-size: cover;
    min-height: 100vh;
  }
  .heading {
    text-align: center;
    margin-bottom: 20px;
    font-size: 24px;
    color: #333
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

    .table-wrapper {
      max-height: 450px; /* or 500px depending on screen size */
      overflow-y: auto;
      margin-bottom: 20px; /* So buttons won't stick to table */
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
      background: #f0f0f0;
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

    .edit-btn {
      background: darkred;
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
