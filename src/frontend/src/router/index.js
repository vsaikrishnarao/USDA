import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import LoginPage from '@/views/LoginPage.vue';
import ViewGroups from '@/views/ViewGroups.vue'; // ✅ Import the new view
import ViewAnimals from "@/views/ViewAnimals.vue";
import SignupPage from "@/views/SignupPage.vue";

const routes = [
    { path: '/', redirect: '/login' },
    { path: '/login', component: LoginPage },
    { path: '/signup', component: SignupPage },
    { path: '/home', component: HomePage, meta: { requiresAuth: true } },
    { path: '/viewgroups', component: ViewGroups, meta: { requiresAuth: true } }, // ✅ New route
    { path: '/viewanimals', component: ViewAnimals, meta: { requiresAuth: true } }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

// ✅ Navigation Guard (unchanged)
router.beforeEach((to, from, next) => {
    const isAuthenticated = sessionStorage.getItem("isAuthenticated");
    if (to.meta.requiresAuth && !isAuthenticated) {
        next('/login');
    } else {
        next();
    }
});

export default router;
