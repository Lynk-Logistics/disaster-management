<template>
  <div>
    <v-row v-for="(item, index) in items" :key="index" style="width:100%" class="ma-0">
      <v-card class="mx-auto" style="width:100%">
        <v-card-title>Donations</v-card-title>

        <v-card-actions>
          <v-btn text>Donated ?</v-btn>
          <v-spacer></v-spacer>
          <v-btn icon @click="show = !show">
            <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
          </v-btn>
        </v-card-actions>

        <v-expand-transition>
          <div v-show="show">
            <v-divider></v-divider>
            <v-card-text
              v-for="(donatedItem, index) in item.items"
              :key="index"
            >{{donatedItem.name}}: {{donatedItem.quantity}}</v-card-text>
          </div>
        </v-expand-transition>
      </v-card>
    </v-row>
  </div>
</template>

<script>
import { getDonations } from "@/api/donor";
import cookiemixin from "@/components/Shared/cookiemixin";

export default {
  name: "MyDonations",
  mixins: [cookiemixin],
  data() {
    return {
      show: true,
      items: [
        {
          items: []
        }
      ]
    };
  },
  mounted() {
    if (this.getCookie("lynkUserId")) {
      getDonations(this.getCookie("lynkUserId")).then(res => {
        if (res.data) {
          var donations = res.data;
          this.items = [donations];
          console.log(this.items);
        }
      });
    }
  }
};
</script>