import {InjectionKey} from 'vue'
// @ts-ignore
import {createStore, Store, useStore as baseUseStore} from 'vuex'
import tabs, {TabsState} from '../store/modules/tabs'
import menu, {MenuState} from '../store/modules/menu'
import {CommonStore} from './help'
//是一种规范
export type RootState = {
    tabs: TabsState,
    menu: MenuState,
}
//导入所有的模块
export const modules = {
    tabs: tabs,
    menu: menu
}
export const key: InjectionKey<Store<RootState>> = Symbol()

export const store = createStore<RootState>({
    modules
}) as CommonStore

// 定义自己的 `useStore` 组合式函数
export function useStore() {
    return baseUseStore(key) as CommonStore
}
