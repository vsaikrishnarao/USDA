<template>
  <div class="signup-container">
    <form @submit.prevent="handleSignup">
      <h2>Sign Up</h2>

      <input v-model="form.firstName" type="text" placeholder="First Name" required />
      <input v-model="form.lastName" type="text" placeholder="Last Name" required />
      <input v-model="form.email" type="email" placeholder="Email" required />
      <input v-model="form.username" type="text" placeholder="Username" required />
      <input v-model="form.password" type="password" placeholder="Password" required />
      <input v-model="form.position" type="text" placeholder="Position" required />

      <label>Is Admin?</label>
      <select v-model="form.isAdminString">
        <option value="false">No</option>
        <option value="true">Yes</option>
      </select>

      <!-- UID Verification Section -->
      <div v-if="form.isAdminString === 'true'">
        <input v-model="adminUID" type="password" placeholder="Enter UID" required />

        <button
            type="button"
            class="green-button"
            @click="verifyUID"
            v-if="!uidVerified"
        >
          Verify UID
        </button>

        <p v-if="uidError" class="error">{{ uidError }}</p>
      </div>

      <!-- Submit Button -->
      <button
          type="submit"
          class="green-button"
          v-if="form.isAdminString === 'false' || uidVerified"
      >
        Submit
      </button>
    </form>
  </div>
</template>

<script>
import { ref, reactive, watch } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

export default {
  name: "SignupPage",
  setup() {
    const router = useRouter();

    const form = reactive({
      firstName: "",
      lastName: "",
      email: "",
      username: "",
      password: "",
      position: "",
      isAdminString: "false",
    });

    const adminUID = ref("");
    const uidVerified = ref(false);
    const uidError = ref("");

    const verifyUID = () => {
      if (adminUID.value === "USDA**") {
        uidVerified.value = true;
        uidError.value = "";
      } else {
        uidVerified.value = false;
        uidError.value = "You are not authorised to be an admin";
      }
    };

    // Reset UID and error if admin toggle changes
    watch(
        () => form.isAdminString,
        (newVal) => {
          if (newVal === "false") {
            adminUID.value = "";
            uidVerified.value = false;
            uidError.value = "";
          }
        }
    );

    const handleSignup = async () => {
      try {
        const userData = {
          ...form,
          isAdmin: form.isAdminString === "true",
        };

        const response = await axios.post(
            `${process.env.VUE_APP_API_BASE_URL}/newusers`,
            userData
        );

        if (response.data.success) {
          alert("User registered successfully.");
          router.push("/login");
        } else {
          alert(response.data.message || "Signup failed.");
        }
      } catch (err) {
        alert("Server error. Please try again.");
      }
    };

    return {
      form,
      handleSignup,
      adminUID,
      verifyUID,
      uidVerified,
      uidError,
    };
  },
};
</script>

<style scoped>
.signup-container {
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
  min-height: 400px;
  font-size: 1rem;
}

input,
select {
  display: block;
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  font-size: inherit;
}

.green-button {
  background: #4caf50;
  color: white;
  padding: 10px;
  border: none;
  cursor: pointer;
  font-size: inherit;
  margin-top: 10px;
  width: 100%;
}

.error {
  color: red;
  font-size: 0.95rem;
  margin-top: 5px;
}
</style>
