// @ts-ignore
import {Module} from "vuex";
import {UserState} from "@/store/modules/user/state.ts";
import {RootState} from "@/store";
import {actions} from "@/store/modules/user/actions.ts";
import {mutations} from "@/store/modules/user/mutations.ts";
import {getters} from "@/store/modules/user/getters.ts";
import {state} from "@/store/modules/user/state.ts";

export const userModule:Module<UserState, RootState> = {
    state,mutations,actions,getters
}
