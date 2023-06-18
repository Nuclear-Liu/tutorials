import {TabsState} from "@/store/modules/tabs/state.ts";
// @ts-ignore
import {GetterTree} from "vuex";
import {RootState} from "@/store";

export type TabsGetters = {
    getTabs(state: TabsState): string[];
}
export const getters: TabsGetters & GetterTree<TabsState, RootState> = {
    getTabs(state: TabsState): string[] {
        return state.tabs;
    }
}
