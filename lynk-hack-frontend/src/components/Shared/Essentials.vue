<template>
  <v-list three-line style="max-height:250px;overflow:scroll">
    <template v-for="(essential, index) in essentials">
      <v-list-item :key="essential.title">
        <v-list-item-content class="ma-0">
          <v-col cols="6" sm="7" xs="6">
            <v-list-item-title v-html="essential.name" align="center"></v-list-item-title>
          </v-col>
          <v-col cols="6" sm="5" xs="6">
            <v-list-item-title v-html="essential.itemCount" align="center"></v-list-item-title>
          </v-col>
        </v-list-item-content>
      </v-list-item>
      <v-divider :key="index" :inset="essential.inset"></v-divider>
    </template>
  </v-list>
</template>

<script>
export default {
  name: "Essentials",
  props: ["essentials"],
  data() {
    return {
      items: [
        {
          itemName: "Bed",
          itemCount: "4"
        },
        {
          itemName: "Biscuit",
          itemCount: "3"
        },
        {
          itemName: "Mosquito coil",
          itemCount: "7"
        },
        {
          itemName: "Lays packet",
          itemCount: "10"
        }
      ]
    };
  }
};
</script>