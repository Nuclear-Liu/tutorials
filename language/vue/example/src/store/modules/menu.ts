// define state
export type MenuState = {
    count: number,
    collapse: boolean
}
export const state: MenuState = {
    count: 0,
    collapse: false
}
// define mutations
export const mutations = {
    setCount: (state: MenuState, count: number) => {
        state.count = count;
    },
    setCollapse: (state: MenuState, collapse: boolean) => {
        state.collapse = collapse;
    }
}

// define actions
export const actions = {}

// define getters
export const getters = {
    getCount: (state: MenuState) => {
        return state.count;
    },
    getCollapse: (state: MenuState) => {
        return state.collapse;
    }
}

export default {
    namespace:true,
    state,
    mutations,
    actions,
    getters
}
