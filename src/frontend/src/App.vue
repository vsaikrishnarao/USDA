<template>
  <router-view></router-view> <!-- Renders the current route -->
</template>

<script>
export default {
  name: "App"
};
</script>

<style>
/* Global CSS Reset */
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
}

*, *::before, *::after {
  box-sizing: border-box;
}
</style>
