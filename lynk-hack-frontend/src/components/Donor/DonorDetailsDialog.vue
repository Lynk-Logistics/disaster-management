<template>
  <div class="text-center">
    <v-bottom-sheet v-model="sheet">
      <v-sheet class="text-center" height="200px">
        <v-btn class="mt-6" text color="red" @click="closeDonorDetailsDialog()">close</v-btn>
        <v-btn
          class="mt-6"
          text
          color="green"
          @click="navigateToDonorLocation(detail.location.coordinates[0], detail.location.coordinates[1])"
        >Navigate</v-btn>
        <div class="py-3">Donar Name: {{detail.name}}</div>
        <div class="py-3">Donar Phone: {{detail.phoneNo}}</div>
      </v-sheet>
    </v-bottom-sheet>
  </div>
</template>

<script>
export default {
  name: "DonorDetailsDialog",
  props: ["closeDonorDetailsDialog", "detail"],
  data() {
    return {
      sheet: true
    };
  },
  methods: {
    navigateToDonorLocation(lat, lng) {
      window.location.href = `https://maps.google.com/?q=${lng},${lat}`;
    }
  }
};
</script>