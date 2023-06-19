import {UserState} from "@/store/modules/user/state.ts";
// @ts-ignore
import {GetterTree} from "vuex";
import {RootState} from "@/store";

export type UserGetters = {
    getCount(state:UserState):number;
}

export const getters : UserGetters&GetterTree<UserState, RootState> = {
    getCount(state: UserState): number {
        return state.count;
    }
}
