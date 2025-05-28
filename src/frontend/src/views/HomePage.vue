<template>
  <div class="home-container">
    <div class="sidebar-container">
      <SideNav @changeView="setActiveView" />
    </div>

    <div class="display-container">
      <component :is="currentComponent" />
    </div>
  </div>
</template>

<script>
import SideNav from "../components/SideNav.vue";
import MapView from "../components/MapView.vue";
import ViewClass from "../views/ViewClass.vue";
import ViewGroup from "../views/ViewGroup.vue";
import ViewLmu from "../views/ViewLmu.vue";
import AddUser from "../views/AddUser.vue";
import DisplayUsers from "../views/DisplayUsers.vue";

export default {
  name: "HomePage",
  components: {
    SideNav,
    MapView,
    ViewClass,
    ViewGroup,
    ViewLmu,
    AddUser,
    DisplayUsers
  },
  data() {
    return {
      activeView: "home"
    };
  },
  computed: {
    currentComponent() {
      switch (this.activeView) {
        case "viewClass":
          return "ViewClass";
        case "viewGroup":
          return "ViewGroup";
        case "viewLmu":
          return "ViewLmu";
        case "addUser":
          return "AddUser";
        case "displayUsers":
          return "DisplayUsers";
        default:
          return "MapView";
      }
    }
  },
  methods: {
    setActiveView(view) {
      this.activeView = view;
    }
  }
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}

.home-container {
  display: flex;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}

.sidebar-container {
  width: 250px;
  position: fixed;
  left: 0;
  top: 0;
  height: 100vh;
}

.display-container {
  margin-left: 250px;
  width: calc(100vw - 250px);
  height: 100vh;
  overflow: hidden;
  position: relative;
}
</style>