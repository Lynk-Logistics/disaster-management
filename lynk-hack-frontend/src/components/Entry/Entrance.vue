<template>
  <v-container class="fill-height entrance" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="2" align="center">
        <v-btn class="entrance-button" width="165px" height="50px" color="red" @click="$router.push('victim')">Victim</v-btn>
      </v-col>
      <v-col cols="12" sm="8" md="2" align="center">
        <v-btn class="entrance-button" width="165px" height="50px" color="yellow" @click="$router.push('volunteer')">Volunteer</v-btn>
      </v-col>
      <v-col cols="12" sm="8" md="2" align="center">
        <v-btn class="entrance-button" width="165px" height="50px" color="green" @click="$router.push('donor')">Donor</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'Entrance'
};
</script>

<style lang="scss">
.entrance {
  .entrance-button {
    border-radius: 25px;
  }
}
</style>
