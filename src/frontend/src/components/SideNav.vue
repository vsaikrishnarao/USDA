<template>
  <nav class="sidebar">
    <ul class="nav navbar-nav">
      <li class="menu-item">
        <a href="#" @click="goHome">
          <span class="icon-text">
            <i class="fa fa-home"></i>
            <span class="link-text">Home</span>
          </span>
        </a>
      </li>

      <!-- Admin Options -->
      <template v-if="isAdmin">
        <li class="menu-item">
          <a href="#" @click="selectClass">
            <span class="icon-text">
              <i class="fa fa-book"></i>
              <span class="link-text">Class</span>
            </span>
          </a>
        </li>
        <li class="menu-item">
          <a href="#" @click="selectGroup">
            <span class="icon-text">
              <i class="fa fa-users"></i>
              <span class="link-text">Group</span>
            </span>
          </a>
        </li>
        <li class="menu-item">
          <a href="#" @click="selectLmu">
            <span class="icon-text">
              <i class="fa fa-map"></i>
              <span class="link-text">LMU</span>
            </span>
          </a>
        </li>
        <li class="menu-item">
          <a href="#" @click="addUser">
            <span class="icon-text">
              <i class="fa fa-map"></i>
              <span class="link-text">Add User</span>
            </span>
          </a>
        </li>
        <li class="menu-item">
          <a href="#" @click="displayUsers">
            <span class="icon-text">
              <i class="fa fa-map"></i>
              <span class="link-text">Users</span>
            </span>
          </a>
        </li>
      </template>

      <!-- Always show Sign Out -->
      <li class="menu-item">
        <a href="#" @click="logout">
          <span class="icon-text">
            <i class="fa fa-sign-out"></i>
            <span class="link-text">Sign Out</span>
          </span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "SideNav",
  computed: {
    isAdmin() {
      return sessionStorage.getItem("isAdmin") === "true";
    }
  },
  methods: {
    goHome() {
      this.$emit("changeView", "home");
    },
    selectClass() {
      this.$emit("changeView", "viewClass");
    },
    selectGroup() {
      this.$emit("changeView", "viewGroup");
    },
    selectLmu() {
      this.$emit("changeView", "viewLmu");
    },
    addUser() {
      this.$emit("changeView","addUser");
    },
    displayUsers() {
      this.$emit("changeView","displayUsers");
    },
    logout() {
      sessionStorage.clear();
      this.$router.push("/login");
    }
  }
};
</script>

<style scoped>
.sidebar {
  width: 250px;
  background: #333;
  color: #fff;
  height: 100vh;
  position: fixed;
  left: 0;
  top: 0;
  overflow-y: auto;
  padding-top: 10px;
}

.nav {
  list-style: none;
  margin: 0;
  padding: 0;
}

.menu-item {
  border-bottom: 1px solid #444;
}

.sidebar a {
  display: flex;
  align-items: center;
  color: #fff;
  padding: 10px 15px;
  text-decoration: none;
  transition: color 0.3s ease;
}

.sidebar a:hover {
  color: #ddd;
}

.icon-text {
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

.link-text {
  display: inline-block;
}

.caret {
  margin-left: auto;
}

.pull-right {
  margin-left: auto;
}

.dropdown-menu {
  background: #444;
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.dropdown-menu li a {
  padding: 10px 20px;
  display: block;
  border-bottom: 1px solid #555;
}

.dropdown-menu li:last-child a {
  border-bottom: none;
}

.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 0.3s ease;
  overflow: hidden;
}
.slide-fade-enter,
.slide-fade-leave-to {
  opacity: 0;
  max-height: 0;
}
.slide-fade-enter-to,
.slide-fade-leave {
  opacity: 1;
  max-height: 500px;
}
</style>
