<template>
  <v-list three-line style="max-height:250px;overflow:scroll">
    <template v-for="(volunteer, index) in volunteers">
      <v-list-item :key="volunteer.title">
        <v-list-item-content class="ma-0">
          <v-col cols="6" sm="6" xs="6">
            <v-list-item-title v-html="volunteer.name || 'NAME_UNKNOWN'" align="left"></v-list-item-title>
          </v-col>
          <v-col cols="6" sm="6" xs="6">
            <v-list-item-title v-html="volunteer.phoneNo" align="left"></v-list-item-title>
          </v-col>
        </v-list-item-content>
      </v-list-item>
      <v-divider :key="index" :inset="volunteer.inset"></v-divider>
    </template>
  </v-list>
</template>

<script>
export default {
  name: "VolunteerList",
  props: ["volunteers"],
  data() {
    return {};
  }
};
</script>