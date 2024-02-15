// define state
import {ITab} from "@/store/type";

export type TabsState = {
    tabs: Array<ITab>
}
export const state: TabsState = {
    tabs: []
}

// define mutations
export const mutations = {
    addTab: (state: TabsState, tab: ITab) => {
        if (state.tabs.some(item => item.path === tab.path))
            return;
        state.tabs.push(tab);
    }
}

// define actions
export const actions = {}

// define getters
export const getters = {
    getTabs:(state:TabsState)=> {
        return state.tabs;
    }
}

export default {
    namespace:true,
    state,
    mutations,
    actions,
    getters
}
