<template>
  <v-list three-line style="max-height:250px;overflow:scroll">
    <template v-for="(donor, index) in donors">
      <v-list-item :key="donor.title">
        <v-list-item-content class="ma-0">
          <v-col cols="6" sm="4" xs="6">
            <v-list-item-title v-html="donor.name" align="left"></v-list-item-title>
          </v-col>
          <v-col cols="6" sm="4" xs="6">
            <v-list-item-title v-html="donor.phoneNo" align="left"></v-list-item-title>
          </v-col>
          <v-col cols="4" sm="4" xs="4" class="hidden-sm-and-down">
            <v-btn color="blue-grey" class="small ma-2 white--text">
              <span
                @click="navigateToDonorLocation(donor.location.coordinates[0], donor.location.coordinates[1])"
              >Navigate</span>
              <v-icon dark>mdi-crosshairs-gps</v-icon>
            </v-btn>
          </v-col>
        </v-list-item-content>
      </v-list-item>
      <v-divider :key="index" :inset="donor.inset"></v-divider>
    </template>
  </v-list>
</template>

<script>
export default {
  name: "DonorList",
  props: ["donors"],
  data() {
    return {};
  },
  methods: {
    navigateToDonorLocation(lat, lng) {
      window.location.href = `https://maps.google.com/?q=${lng},${lat}`;
    }
  }
};
</script>