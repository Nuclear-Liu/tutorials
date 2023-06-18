import {TabsState} from "@/store/modules/tabs/state.ts";
// @ts-ignore
import {MutationTree} from "vuex";

export type TabsMutations = {
    // @ts-ignore
    addTab(state: TabsState, tab: string);
}

export const mutations: TabsMutations &MutationTree<TabsState> = {
    addTab(state: TabsState, tab: string) {
        state.tabs.push(tab)
    }
}
