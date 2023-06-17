import {InjectionKey} from "vue";
// @ts-ignore
import {createStore, Store, useStore as baseUseStore} from "vuex";
import type {ITab} from '@/store/type/index.ts'

export interface State {
    count: number,
    collapse: boolean,
    tabList: Array<ITab>
}

export const key: InjectionKey<Store<State>> = Symbol()

export const store = createStore<State>({
    state: {
        count: 0,
        collapse: false,
        tabList: []
    },
    mutations: {
        setCount(state: State, count: number) {
            state.count = count;
        },
        setCollapse: (state: State, collapse: boolean) => {
            state.collapse = collapse;
        },
        addTab: (state: State, tab: ITab) => {
            // 判断是否已经存在
            if (state.tabList.some(item => item.path === tab.path)) {
                return;
            }
            state.tabList.push(tab);
        }
    },
    getters: {
        getCount(state: State) {
            return state.count;
        },
        getCollapse: (state: State) => {
            return state.collapse;
        },
        getTabList:(state: State) => {
            return state.tabList;
        }
    }
})

export function useStore() {
    return baseUseStore(key)
}