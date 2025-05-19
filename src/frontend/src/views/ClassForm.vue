<template>
  <div class="class-form-card">
    <h2 class="form-title">{{ mode === 'add' ? 'Add New Class' : 'Edit Class' }}</h2>

    <form @submit.prevent="handleSubmit" class="class-form">
      <div class="form-group">
        <label for="label">Label *</label>
        <input id="label" v-model="formData.label" placeholder="Enter label" required class="form-input" />
      </div>

      <div class="form-group">
        <label for="description">Description</label>
        <input id="description" v-model="formData.description" placeholder="Enter description" class="form-input" />
      </div>

      <div class="form-group">
        <label for="aue">AUE *</label>
        <input id="aue" type="number" step="0.01" v-model="formData.aue" placeholder="Enter AUE" required class="form-input" />
      </div>

      <div class="form-actions">
        <button type="button" @click="$emit('close')" class="btn btn-cancel">Cancel</button>
        <button type="submit" class="btn btn-submit">
          {{ mode === 'add' ? 'Add Class' : 'Save Changes' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ClassForm",
  props: {
    mode: { type: String, required: true },
    existingClass: { type: Object, default: null },
    nextClassId: { type: Number, default: null }
  },
  data() {
    return {
      formData: {
        id: null,
        label: "",
        description: "",
        aue: null
      }
    };
  },
  created() {
    if (this.mode === "edit" && this.existingClass) {
      this.formData = {
        id: this.existingClass.id,
        label: this.existingClass.label,
        description: this.existingClass.description,
        aue: this.existingClass.aue
      };
    }
    if (this.mode === "add" && this.nextClassId) {
      this.formData.id = this.nextClassId;
    }
  },
  methods: {
    async handleSubmit() {
      try {
        const url = this.mode === 'add'
            ? `${process.env.VUE_APP_API_BASE_URL}/classes/add`
            : `${process.env.VUE_APP_API_BASE_URL}/classes/edit`;

        const response = await axios.post(url, this.formData);

        if (response.data && response.data.includes("already exists")) {
          alert(response.data);
        } else {
          alert(this.mode === 'add' ? 'Class added successfully!' : 'Class updated successfully!');
          this.$emit('saved');
        }
      } catch (err) {
        console.error("Error:", err);
        alert('Failed to save class.');
      }
    }
  }
};
</script>

<style scoped>
/* Same clean form styling like GroupForm.vue */
.class-form-card {
  background: white;
  padding: 25px;
  border-radius: 8px;
  max-width: 500px;
  margin: 0 auto;
  box-shadow: 0 2px 8px rgba(0,0,0,0.2);
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
  justify-content: flex-end;
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
