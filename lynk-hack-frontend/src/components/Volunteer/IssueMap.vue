<template>
  <div class="maps-holder">
    <div id="maps-family" style="height: 400px"></div>
    <issue-detail v-if="showIssueDetailDialog" :issueId="issueId" :closeIssueDetailPopup="closeIssueDetailPopup"></issue-detail>
  </div>
</template>

<script>
import Maps from "@/plugins/maps";
import IssueDetail from "@/components/Shared/IssueDetail.vue";

export default {
  name: "IssueMap",
  props: ["locations"],
  components: {
    IssueDetail
  },
  data() {
    return {
      mapsHandler: "",
      showIssueDetailDialog: false,
      isLocationsSet: false,
      issues: [],
      issueId: null
    };
  },
  methods: {
    openIssueDetailDialog() {
      this.showIssueDetailDialog = true;
    },
    openMap(response) {
      this.mapsHandler = new Maps({
        locations: response,
        mapsDiv: "maps-family"
      });
      this.mapsHandler.showContents(this.setLocationSelectCallBack);
      this.isLocationsSet = true;
    },
    setLocationSelectCallBack(item) {
      this.issueId = item.issueId;
      this.openIssueDetailDialog();
    },
    closeIssueDetailPopup () {
      this.showIssueDetailDialog = false
    }
  },
  mounted() {
    setTimeout(() => {
      this.openMap(this.locations);
    }, 1000);
  }
};
</script>