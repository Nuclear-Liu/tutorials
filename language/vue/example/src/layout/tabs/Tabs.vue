<script setup lang="ts">
import {computed, onMounted, ref, watch} from 'vue'
import {useStore} from "@/store";
import {useRoute, useRouter} from "vue-router";
import type {ITab} from "@/store/type";

const store = useStore();
const tabList = computed(() => {
    return store.getters['getTabList'];
})
const activeTab = ref('');
const route:any = useRoute();
const router = useRouter();
const setActiveTab = () => {
    activeTab.value = route.path;
}

// 解决刷新丢失
const beforeRefresh = () => {
    window.addEventListener('beforeunload', () => {
        sessionStorage.setItem("tabViews", JSON.stringify(tabList.value))
    })
    let tabSession = sessionStorage.getItem("tabViews")
    console.log(tabSession)
    if (tabSession) {
        let oldTabs = JSON.parse(tabSession);
        if (oldTabs.length > 0) {
            // oldTabs.forEach(tab=>store.commit('addTab',tab));
            store.state.tabList = oldTabs;
        }
    }
}

const removeTab = (targetName: string) => {
    if (targetName === '/dashboard') return;
    console.log(targetName)
    const tabs = tabList.value;
    let activeName = activeTab.value;
    if (activeName === targetName) {
        tabs.forEach((tab: ITab, index: number) => {
            if (tab.path === targetName) {
                const nextTab = tabs[index + 1] || tabs[index - 1]
                if (nextTab) {
                    activeName = nextTab.path
                }
            }
        })
    }
    activeTab.value = activeName;
    store.state.tabList = tabs.filter((tab: ITab) => tab.path != targetName)
    router.push({
        path: activeName
    })
}
const tabClick = (tab: any) => {
    const {props} = tab;
    console.log(props)
    // 跳转路由
    router.push({
        path: props.name
    })
}

const addTab = () => {
    const {path, meta} = route;
    const tab: ITab = {
        path: path,
        title: meta.title
    }
    store.commit('addTab', tab);
}

onMounted(() => {
    // 解决刷新问题
    beforeRefresh();
    // 设置当前路由
    setActiveTab();
    // 把当前路由添加到选项卡数据
    addTab();
})

watch(() => route.path, () => {
    // 设置当前路由
    setActiveTab();
    // 把当前路由添加到选项卡数据
    addTab();
})
</script>

<template>
    <el-tabs v-model="activeTab" type="border-card" closable class="demo-tabs" @tab-remove="removeTab"
             @tab-click="tabClick">
        <el-tab-pane v-for="item in tabList" :key="item.path" :label="item.title" :name="item.path"></el-tab-pane>
    </el-tabs>
</template>

<style scoped>

.demo-tabs > .el-tabs__content {
    padding: 32px;
    color: #3377ee;
    font-size: 32px;
    font-weight: 600;
    background: #828994;
}
</style>
