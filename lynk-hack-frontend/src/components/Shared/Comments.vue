<template>
  <v-list three-line style="max-height:250px;overflow:scroll">
      <v-row>
        <v-col cols="12" >
          <v-text-field
            v-model="message"
            :append-outer-icon="message ? 'mdi-send' : 'mdi-microphone'"
            filled
            clear-icon="mdi-close-circle"
            clearable
            label="Comment here.."
            type="text"
            @click:append-outer="sendMessage"
            @click:clear="clearMessage"
          ></v-text-field>
        </v-col>

      </v-row>
    <template v-for="(item, index) in items">
      <v-list-item
        :key="item.title"
      >
        <v-list-item-content class="ma-0">
          <v-col cols="12" sm="9" >
            <v-list-item-title v-html="item.comment"></v-list-item-title>
          </v-col>
          <v-col cols="12" sm="3" align="left">
            <v-list-item-subtitle v-html="item.timestamp"></v-list-item-subtitle>
          </v-col>
        </v-list-item-content>
      </v-list-item>
      <v-divider
        :key="index"
        :inset="item.inset"
      ></v-divider>
    </template>
  </v-list>
</template>

<script>
export default {
  name: "Comments",
  data () {
    return {
      items: [
        {
          comment: 'Brunch this weekend?',
          timestamp: '20-10-2019'
        },
        {
          comment: 'Brunch this weekend?',
          timestamp: '20-10-2019'
        },
        {
          comment: 'Brunch this weekend?',
          timestamp: '20-10-2019'
        },
        {
          comment: 'Brunch this weekend?',
          timestamp: '20-10-2019'
        }
      ],
      message: '',
    }
  },
    methods: {
      sendMessage () {
        this.resetIcon()
        this.clearMessage()
      },
      clearMessage () {
        this.message = ''
      },
    },
}
</script>

<style lang="scss">
.v-text-field__details {
  display: none;
}
</style>