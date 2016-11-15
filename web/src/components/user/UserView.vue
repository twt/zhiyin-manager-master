<template>
  <h4>{{msg}}</h4>
  <table class="mdl-data-table mdl-shadow--1dp">
    <thead>
      <tr>
        <th class="mdl-data-table__cell--non-numeric">name</th>
        <th>email</th>
        <th>telephone</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="user in users">
        <td class="mdl-data-table__cell--non-numeric">{{user.name}}</td>
        <td>{{user.email}}</td>
        <td>{{user.telephone}}</td>
      </tr>
    </tbody>
  </table>

</template>

<script>
import UserService from './UserService.js'

export default {
  data () {
    return {
      // note: changing this line won't causes changes
      // with hot-reload because the reloaded component
      // preserves its current state and we are modifying
      // its initial state.
      msg: 'Hello World!',
      users: null
    }
  },
  route: {
    data: function (transition) {
      return {
        users: UserService.getAllUsers()
      }
    }
  }
}
</script>
