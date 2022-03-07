import {useState} from 'react';
import { StatusBar } from 'expo-status-bar';
import { StyleSheet, View, ScrollView } from 'react-native';
import { Box, Text, Heading, VStack, FormControl, Input, Link, Button, HStack, Center, NativeBaseProvider } from "native-base";
import Header from "./components/Header"
import Form from "./components/Form"
const LinearGradient = require("expo-linear-gradient").LinearGradient;

export default function App() {
  const config = {
    dependencies: {
      "linear-gradient": LinearGradient
    }
  };

  return (
    <NativeBaseProvider config={config}>
      <Box w="100%" h="100%" bg={{
           linearGradient: {
             colors: ["lightBlue.200", "violet.200"]
           }
         }}>
        <Header/>
        <Center flex={1} px="3">
          <Form/>
        </Center>
      </Box>
    </NativeBaseProvider>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  title: {
    paddingVertical: 32,
    paddingHorizontal: 32,
    fontFamily: 'Inter-Bold',
    fontWeight: '700',
    fontSize: 30,
  },
  inputField: {
    paddingVertical: 8,
  }
});
