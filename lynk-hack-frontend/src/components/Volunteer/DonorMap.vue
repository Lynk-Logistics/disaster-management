<template>
  <div class="maps-holder">
    <div id="donor-family" style="height: 400px"></div>
    <donor-details-dialog v-if="showDonorDetailsDialog" :closeDonorDetailsDialog="closeDonorDetailsDialog" :detail="donorDetail"></donor-details-dialog>
  </div>
</template>

<script>
import Maps from "@/plugins/maps";
import DonorDetailsDialog from '@/components/Donor/DonorDetailsDialog.vue'
import { getDonorByDonorId } from "@/api/donor";

export default {
  name: "DonorMap",
  props: ["locations"],
  data() {
    return {
      mapsHandler: "",
      isLocationsSet: false,
      showDonorDetailsDialog: false,
      donorDetail: ''
    };
  },
  components: {
    DonorDetailsDialog
  },
  methods: {
    openMap(response) {
      this.mapsHandler = new Maps({
        locations: this.locations,
        mapsDiv: "donor-family"
      });
      this.mapsHandler.showContents(this.setLocationSelectCallBack);
      this.isLocationsSet = true;
    },
    setLocationSelectCallBack(item) {
      getDonorByDonorId(item.donarId).then(res => {
        if (res.data) {
          this.donorDetail = res.data;
          this.showDonorDetailsDialog = true;
        }
      });
    },
    closeDonorDetailsDialog () {
      this.showDonorDetailsDialog = false
    }
  },
  mounted() {
    setTimeout(() => {
      this.openMap(this.locations);
    }, 3000);
  }
};
</script>