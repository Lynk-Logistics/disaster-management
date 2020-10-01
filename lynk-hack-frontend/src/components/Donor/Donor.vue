<template>
  <v-container class="victim">
    <v-row>
      <v-col cols="12" sm="6" xs="12">
        <div id="chart">
          <!-- <apexchart type=pie width=380 :options="chartOptions" :series="series" /> -->
        </div>
      </v-col>
      <v-col cols="12" sm="6" xs="12">
        <my-donations v-if="donations && showMyDonations"></my-donations>
        <donor-items v-else></donor-items>
        <v-btn
          color="green"
          class="mt-5"
          @click="donate()"
          v-if="donations && showMyDonations"
        >Donate</v-btn>
      </v-col>
    </v-row>
    <authentication-dialog v-if="showAuthenticationPopup" userType="donor"></authentication-dialog>
  </v-container>
</template>

<script>
import VueApexCharts from "vue-apexcharts";
import DonorItems from "@/components/Donor/DonorItems.vue";
import MyDonations from "@/components/Donor/MyDonations.vue";
import { getEssentialRequirements } from "@/api/donor";
import AuthenticationDialog from "@/components/Shared/AuthenticationDialog.vue";
import cookiemixin from "@/components/Shared/cookiemixin";

export default {
  name: "Donor",
  components: {
    VueApexCharts,
    DonorItems,
    MyDonations,
    AuthenticationDialog
  },
  mixins: [cookiemixin],
  data() {
    return {
      options: {
        chart: {
          width: 500,
          type: "pie"
        },
        labels: ["Team A - 10", "Team B", "Team C", "Team D", "Team E"],
        series: [44, 55, 13, 43, 22],
        responsive: [
          {
            breakpoint: 480,
            options: {
              chart: {
                width: 375
              },
              legend: {
                position: "bottom"
              }
            }
          }
        ]
      },
      donations: true,
      showMyDonations: true,
      showAuthenticationPopup: false
    };
  },
  methods: {
    donate() {
      if (this.getCookie("lynkUser")) {
        this.showMyDonations = false;
      } else {
        this.showAuthenticationPopup = true;
      }
    }
  },
  mounted() {
    getEssentialRequirements().then(res => {
      if (res.data) {
        var essentialRequirements = res.data.essentialRequirements;
        this.options.labels = essentialRequirements.map(
          essentialRequirement => {
            return (
              essentialRequirement["name"] +
              " - " +
              essentialRequirement["quantity"]
            );
          }
        );
        this.options.series = essentialRequirements.map(
          essentialRequirement => {
            return essentialRequirement["quantity"];
          }
        );
        var chart = new ApexCharts(
          document.querySelector("#chart"),
          this.options
        );
        chart.render();
      }
    });
  }
};
</script>