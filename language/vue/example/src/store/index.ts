import {InjectionKey} from "vue";
// @ts-ignore
import {createStore, Store, useStore as baseUseStore} from "vuex";
import {TabsState} from "@/store/modules/tabs/state.ts";
import {UserState} from "@/store/modules/user/state.ts";
import {tabsModule} from "@/store/modules/tabs";
import {userModule} from "@/store/modules/user";

export type RootState = {
    tabs: TabsState,
    user: UserState
}

const modules = {
    tabs:tabsModule,
    user:userModule
}

export const key: InjectionKey<Store<RootState>> = Symbol()

export const store = createStore<RootState>({
    modules
})

export function useStore() {
    return baseUseStore(key)
}
