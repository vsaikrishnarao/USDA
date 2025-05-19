<template>
  <div class="group-form-card">
    <h2 class="form-title">{{ mode === 'add' ? 'Add New Group' : 'Edit Group' }}</h2>

    <form @submit.prevent="handleSubmit" class="group-form">
      <!-- Short Name -->
      <div class="form-group">
        <label for="shortName">Short Name *</label>
        <input
            id="shortName"
            v-model="formData.nameGrpShort"
            placeholder="Enter short name"
            required
            class="form-input"
        />
      </div>

      <!-- Long Name -->
      <div class="form-group">
        <label for="longName">Long Name *</label>
        <input
            id="longName"
            v-model="formData.nameGrpLong"
            placeholder="Enter long name"
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

      <!-- Active Toggle -->
      <div class="form-group active-toggle-group">
        <label>Active Status</label>
        <label class="toggle-switch">
          <input
              type="checkbox"
              v-model="formData.active"
              class="toggle-input"
          />
          <span class="toggle-slider"></span>
          <span class="toggle-label">{{ formData.active ? 'Active' : 'Inactive' }}</span>
        </label>
      </div>

      <!-- Footer Buttons -->
      <div class="form-actions">
        <button type="button" @click="$emit('close')" class="btn btn-cancel">Cancel</button>
        <button type="submit" class="btn btn-submit">
          {{ mode === 'add' ? 'Add Group' : 'Save Changes' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "GroupForm",
  props: {
    mode: { type: String, required: true },
    existingGroup: { type: Object, default: null },
    nextGroupId: { type: Number, default: null }
  },
  data() {
    return {
      formData: {
        id: null,
        nameGrpShort: "",
        nameGrpLong: "",
        active: true,
        dateValid: ""
      },
      startDate: "",
      endDate: ""
    };
  },
  created() {
    if (this.mode === "edit" && this.existingGroup) {
      this.formData = {
        id: this.existingGroup.id,
        nameGrpShort: this.existingGroup.nameGrpShort,
        nameGrpLong: this.existingGroup.nameGrpLong,
        active: this.existingGroup.active,
        dateValid: this.existingGroup.dateValid
      };

      const match = this.formData.dateValid.match(/\[(.*?),(.*?)\)/);
      if (match && match.length === 3) {
        this.startDate = match[1].trim();
        this.endDate = match[2].trim();
      }
    }
    if (this.mode === "add" && this.nextGroupId) {
      this.formData.id = this.nextGroupId;
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
          alert('Start Date is mandatory!');
          return;
        }
        this.updateDateRange();

        const url = this.mode === 'add'
            ? `${process.env.VUE_APP_API_BASE_URL}/groups/add`
            : `${process.env.VUE_APP_API_BASE_URL}/groups/edit`;

        const response = await axios.post(url, this.formData);

        if (response.data && response.data.includes("already exists")) {
          alert(response.data); // Example: "Group with the same short name already exists."
        } else {
          alert(this.mode === 'add' ? 'Group added successfully!' : 'Group updated successfully!');
          this.$emit('saved');
        }
      } catch (err) {
        console.error("Error:", err);
        alert('Failed to save group.');
      }
    }
  }
};
</script>

<style scoped>
/* Clean CSS without notification classes */
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

.active-toggle-group {
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.toggle-switch {
  display: flex;
  align-items: center;
}

.toggle-slider {
  width: 50px;
  height: 24px;
  background: #ccc;
  border-radius: 24px;
  position: relative;
  transition: background 0.3s;
}

.toggle-slider:before {
  content: "";
  position: absolute;
  height: 20px;
  width: 20px;
  background: white;
  border-radius: 50%;
  left: 2px;
  top: 2px;
  transition: 0.4s;
}

.toggle-input:checked + .toggle-slider {
  background: #4CAF50;
}

.toggle-input:checked + .toggle-slider:before {
  transform: translateX(26px);
}

.toggle-label {
  margin-left: 10px;
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
