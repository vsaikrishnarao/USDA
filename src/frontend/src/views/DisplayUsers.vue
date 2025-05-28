<template>
  <div class="view-users">
    <h2 class="heading">User Management</h2>

    <!-- Table -->
    <div class="table-wrapper">
      <table class="user-table">
        <thead>
        <tr>
          <th><input type="checkbox" v-model="selectAll" @change="toggleSelectAll" /></th>
          <th>Username</th>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Email</th>
          <th>Position</th>
          <th>Admin</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in users" :key="user.username">
          <td><input type="checkbox" v-model="selectedUsers" :value="user" /></td>
          <td>{{ user.username }}</td>
          <td>{{ user.firstName }}</td>
          <td>{{ user.lastName }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.position }}</td>
          <td>{{ user.isAdmin ? 'Yes' : 'No' }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- Delete Button -->
    <div class="footer-buttons">
      <button class="btn delete-btn" @click="deleteSelectedUsers" :disabled="selectedUsers.length === 0">
        Delete
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "DisplayUsers",
  data() {
    return {
      users: [],
      selectedUsers: [],
      selectAll: false
    };
  },
  created() {
    this.fetchUsers();
  },
  methods: {
    fetchUsers() {
      axios.get(`${process.env.VUE_APP_API_BASE_URL}/users`)
          .then(response => {
            if (response.data.success) {
              this.users = response.data.users;
            } else {
              alert(response.data.message);
            }
          })
          .catch(error => {
            console.error("Error fetching users:", error);
          });
    },
    toggleSelectAll() {
      if (this.selectAll) {
        this.selectedUsers = [...this.users];
      } else {
        this.selectedUsers = [];
      }
    },
    deleteSelectedUsers() {
      if (!confirm("Are you sure you want to delete the selected user(s)?")) return;

      const deletionPromises = this.selectedUsers.map(user =>
          axios.delete(`${process.env.VUE_APP_API_BASE_URL}/users/${user.username}`)
      );

      Promise.all(deletionPromises)
          .then(() => {
            this.fetchUsers();
            this.selectedUsers = [];
            this.selectAll = false;
          })
          .catch(error => {
            console.error("Error deleting users:", error);
          });
    }
  }
};
</script>

<style scoped>
.view-users {
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
.table-wrapper {
  max-height: 450px;
  overflow-y: auto;
  margin-bottom: 20px;
}
.user-table {
  width: 100%;
  border-collapse: collapse;
  background-color: #f3f0e3;
  opacity: 0.95;
}
.user-table th,
.user-table td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: center;
}
.user-table th {
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
  border-radius: 4px;
}
.delete-btn {
  background-color: crimson;
  color: white;
}
</style>