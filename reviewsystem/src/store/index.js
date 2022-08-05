import { createStore } from 'vuex'

export default createStore({
    state: {
        identity: '',
        user_id: '111',
        teacherHead: 'reviews',
        studentHead: 'projects',
        adminHead: 'manageUser'
    },
    getters: {
    },
    mutations: {
      identitySelect(state, identity) {
        state.identity = identity;
    },
    idSubmint(state, id) {
        state.user_id = id;
    }
    },
    actions: {},
    modules: {}
})