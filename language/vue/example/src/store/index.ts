import {InjectionKey} from "vue";
// @ts-ignore
import {createStore, Store, useStore as baseUseStore} from "vuex";
import {TabsState} from "@/store/modules/tabs/state.ts";

export interface RootState {
    tabState:TabsState
}

export const key: InjectionKey<Store<RootState>> = Symbol()

export const store = createStore<RootState>({
    modules
}) as CommonStore

export function useStore() {
    return baseUseStore(key)
}
