import {UserState} from "@/store/modules/user/state.ts";
// @ts-ignore
import {MutationTree} from "vuex";

export type UserMutations = {
    addCount(state:UserState, count:number):void;
}

export const mutations:UserMutations&MutationTree<UserState> = {
    addCount(state: UserState, count: number) {
        state.count = count;
    }
}
