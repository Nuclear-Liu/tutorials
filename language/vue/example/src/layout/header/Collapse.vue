<script setup lang="ts">
import {Expand, Fold} from "@element-plus/icons-vue";
import {computed} from "vue";
import {useStore} from "@/store/index.ts"

const store = useStore();

const status = computed(() => {
    return store.getters['menu/getCollapse'];
});
const toggle = () => {
    store.commit('menu/setCollapse', !status.value);
}
</script>

<template>
    <el-icon class="icons" @click="toggle">
        <component :is="status ? Expand : Fold"/>
    </el-icon>
</template>

<style scoped lang="scss">
.icons {
    display: flex;
    align-items: center;
    float: left;
    font-size: 26px;
    color: #303133;
    cursor: pointer;
}
</style>
