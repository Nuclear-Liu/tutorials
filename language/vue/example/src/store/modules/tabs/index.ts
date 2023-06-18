// @ts-ignore
import {Module} from "vuex";
import {state, TabsState} from "@/store/modules/tabs/state.ts";
import {RootState} from "@/store";
import {actions} from "@/store/modules/tabs/actions.ts"
import {getters} from "@/store/modules/tabs/getters.ts"
import {mutations} from "@/store/modules/tabs/mutations.ts";


export const tabsModule: Module<TabsState, RootState> = {
    state, actions, mutations, getters
}
