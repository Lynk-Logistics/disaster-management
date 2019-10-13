<template>
  <v-app>
    <v-app-bar app>
      <v-toolbar-title class="headline text-uppercase" @click="goToHomePage()">
        <span>LYNC</span>
        <span class="font-weight-light"> Hack</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn
        text
        href="https://github.com/vuetifyjs/vuetify/releases/latest"
        target="_blank"
      >
        <span class="mr-2">2.0</span>
      </v-btn>
    </v-app-bar>

    <v-content>
      <router-view/>
    </v-content>
  </v-app>
</template>

<script>

export default {
  name: 'App',
  data: () => ({
    //
  }),
  methods: {
    goToHomePage () {
      window.location.href = '/'
    }
  }
};
</script>
