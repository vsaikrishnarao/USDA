<template>
  <div class="login-container">
    <form @submit.prevent="handleLogin">
      <h2>Login</h2>
      <input type="text" v-model="username" placeholder="Username" required />
      <input type="password" v-model="password" placeholder="Password" required />
      <button type="submit">Login</button>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

export default {
  name: "LoginPage",
  setup() {
    const router = useRouter();
    const username = ref("");
    const password = ref("");
    const errorMessage = ref("");

    const handleLogin = async () => {
      try {
        const response = await axios.post(
            // "http://localhost:8095/api/login",
            `${process.env.VUE_APP_API_BASE_URL}/login`,
            null,
            { params: { username: username.value, password: password.value } }
        );

        if (response.data.success) {
          sessionStorage.setItem("isAuthenticated", "true");
          sessionStorage.setItem("isAdmin", response.data.isAdmin); // ✅ Save admin flag
          router.push("/home");
        } else {
          errorMessage.value = response.data.message || "Login failed.";
        }
      } catch (err) {
        errorMessage.value = "Server error. Please try again.";
      }
    };

    return { username, password, handleLogin, errorMessage };
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url('@/assets/static.png') no-repeat center center;
  background-size: cover;
}

form {
  background: rgba(255, 255, 255, 0.1);
  padding: 40px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  text-align: center;
  min-width: 300px;
  min-height: 300px;
  font-size: 1rem;
}

input {
  display: block;
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  font-size: inherit;
}

button {
  background: #4caf50;
  color: white;
  padding: 10px;
  border: none;
  cursor: pointer;
  font-size: inherit;
}

.error {
  color: red;
  margin-top: 10px;
  font-size: 20px;
}
</style>

