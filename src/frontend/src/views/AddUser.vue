<template>
  <div class="add-user-container" :style="backgroundStyle">
    <div class="class-form-card">
      <h2 class="form-title">Add New User</h2>

      <form @submit.prevent="handleSubmit" class="class-form">
        <div class="form-group">
          <label for="username">Username *</label>
          <input
              id="username"
              v-model="formData.username"
              placeholder="Enter username"
              required
              class="form-input"
          />
        </div>

        <div class="form-group">
          <label for="password">Password *</label>
          <input
              id="password"
              type="password"
              v-model="formData.password"
              placeholder="Enter password"
              required
              class="form-input"
          />
        </div>

        <div class="form-group">
          <label for="isAdmin">Is Admin? *</label>
          <select
              id="isAdmin"
              v-model="formData.isAdmin"
              required
              class="form-input"
          >
            <option :value="true">Yes</option>
            <option :value="false">No</option>
          </select>
        </div>

        <div class="form-actions">
          <button type="reset" class="btn btn-cancel">Clear</button>
          <button type="submit" class="btn btn-submit">Add User</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import backgroundImage from "@/assets/static.png";

export default {
  name: "AddUser",
  data() {
    return {
      formData: {
        username: "",
        password: "",
        isAdmin: false
      },
      backgroundStyle: {
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: "cover",
        backgroundRepeat: "no-repeat",
        backgroundPosition: "center",
        minHeight: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        padding: "20px"
      }
    };
  },
  methods: {
    async handleSubmit() {
      try {
        const response = await axios.post(`${process.env.VUE_APP_API_BASE_URL}/users`, this.formData);
        if (response.data.success) {
          alert("User added successfully!");
          this.formData.username = "";
          this.formData.password = "";
          this.formData.isAdmin = false;
        } else {
          alert(response.data.message || "Failed to add user.");
        }
      } catch (err) {
        console.error(err);
        alert("Server error occurred.");
      }
    }
  }
};
</script>

<style scoped>
.class-form-card {
  background: white;
  padding: 25px;
  border-radius: 8px;
  max-width: 500px;
  width: 100%;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}
.form-title {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}
.class-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
.form-group {
  display: flex;
  flex-direction: column;
}
.form-group label {
  font-weight: 600;
  margin-bottom: 5px;
}
.form-input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.form-actions {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}
.btn {
  padding: 10px 18px;
  border: none;
  border-radius: 5px;
  font-weight: 600;
  cursor: pointer;
}
.btn-cancel {
  background: #eee;
  color: #333;
}
.btn-submit {
  background: #4CAF50;
  color: white;
}
</style>
<!--test-->
