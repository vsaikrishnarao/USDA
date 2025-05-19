<template>
  <div class="group-form-card">
    <h2 class="form-title">Edit LMU</h2>

    <form @submit.prevent="handleSubmit" class="group-form">
      <!-- Label -->
      <div class="form-group">
        <label for="label">Label *</label>
        <input
            id="label"
            v-model="formData.label"
            placeholder="Enter label"
            required
            class="form-input"
        />
      </div>

      <div class="form-group">
        <label for="type">Type *</label>
        <select
            id="type"
            v-model="formData.type"
            required
            class="form-input"
        >
          <option value="">Select type</option>
          <option value="pasture">Pasture</option>
          <option value="corral">Corral</option>
          <option value="alley">Alley</option>
        </select>
      </div>


      <!-- Grazable Factor -->
      <div class="form-group">
        <label for="grzblFactor">Grazable Factor *</label>
        <input
            id="grzblFactor"
            type="number"
            step="0.01"
            v-model="formData.grzblFactor"
            placeholder="Enter grazing factor"
            required
            class="form-input"
        />
      </div>

      <!-- Date Range -->
      <div class="form-group">
        <label>Date Range *</label>
        <div class="date-range-picker">
          <input
              type="date"
              v-model="startDate"
              @change="updateDateRange"
              required
              class="date-input"
          />
          <span class="date-separator">to</span>
          <input
              type="date"
              v-model="endDate"
              @change="updateDateRange"
              class="date-input"
          />
        </div>
        <small class="hint">Format: [start date, end date)</small>
      </div>

      <!-- Footer Buttons -->
      <div class="form-actions">
        <button type="button" @click="$emit('close')" class="btn btn-cancel">
          Cancel
        </button>
        <button type="submit" class="btn btn-submit">Save Changes</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LmuForm",
  props: {
    mode: { type: String, required: true }, // only "edit" here
    existingLmu: { type: Object, required: true }
  },
  data() {
    return {
      formData: {
        gid: null,
        label: "",
        type: "",
        grzblFactor: null,
        dateValid: ""
      },
      startDate: "",
      endDate: ""
    };
  },
  created() {
    if (this.mode === "edit" && this.existingLmu) {
      this.formData = {
        gid: this.existingLmu.gid,
        label: this.existingLmu.label,
        type: this.existingLmu.type,
        grzblFactor: this.existingLmu.grzblFactor,
        dateValid: this.existingLmu.dateValid?.value || ""  // 🔥 correctly extract value
      };

      if (this.formData.dateValid) {
        const match = this.formData.dateValid.match(/\[(.*?),(.*?)\)/);
        if (match && match.length === 3) {
          this.startDate = match[1].trim();
          this.endDate = match[2].trim();
        }
      }
    }
  },
  methods: {
    updateDateRange() {
      if (this.startDate) {
        this.formData.dateValid = `[${this.startDate},${this.endDate || ""})`;
      }
    },
    async handleSubmit() {
      try {
        if (!this.startDate) {
          alert("Start Date is mandatory!");
          return;
        }
        this.updateDateRange();

        await axios.post(`${process.env.VUE_APP_API_BASE_URL}/lmu/edit`, this.formData);
        alert("LMU updated successfully!");
        this.$emit("saved");
      } catch (err) {
        console.error("Error:", err);
        alert("Failed to update LMU.");
      }
    }
  }
};
</script>

<style scoped>
.group-form-card {
  background: white;
  padding: 25px;
  border-radius: 8px;
  max-width: 500px;
  margin: 0 auto;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.form-title {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

.group-form {
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

.date-range-picker {
  display: flex;
  gap: 10px;
}

.date-input {
  padding: 8px;
  width: 100%;
}

.date-separator {
  font-size: 14px;
  color: #666;
  align-self: center;
}

.hint {
  font-size: 12px;
  color: #777;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 15px;
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
