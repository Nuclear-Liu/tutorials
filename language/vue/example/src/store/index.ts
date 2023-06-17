import {InjectionKey} from "vue";
// @ts-ignore
import {createStore, Store, useStore as baseUseStore} from "vuex";

export interface State {
    count: number,
    collapse: boolean
}

export const key: InjectionKey<Store<State>> = Symbol()

export const store = createStore<State>({
    state: {
        count: 0, collapse: false
    },
    mutations: {
        setCount(state: State, count: number) {
            state.count = count;
        },
        setCollapse: (state: State, collapse: boolean) => {
            state.collapse = collapse;
        }
    },
    getters: {
        getCount(state: State) {
            return state.count;
        },
        getCollapse: (state: State) => {
            return state.collapse;
        }
    }
})

export function useStore() {
    return baseUseStore(key)
}