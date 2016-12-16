# Plantt
#### The Garderner's Handiest Tool
## jonahnorbury-pset6
Final App project "Plantt" for the course "Native App Studio", 
University of Amsterdam (UvA), 16-12-2016.

Created with love by Jonah Norbury, 10742891.

## Introduction
### Requirements
The native android app within this repository is for Android API 15 and above.
An internet connection is required for full usage of the app.

### Purpose
The following app was made as a part of the minor in Programming of the UvA.
During week two of the app, a peer review assessment was undergone to ensure
the app lives up to the standards as given on
https://appsf.mprog.nl/psets/your-own as of 16-12-16.

## The App
### Why?
Everyone has plants, everyone forgets to water them.

### What does it do?
The app bases on allowing users to use the wiki open-api for the purpose of implementing restful JSON through asynctasks, which allows very specific data to be requested at a fast and efficient rate. Only the necessary bits of data travel the interwebs.

### How do I use it?
Starting off you need to login or register on "my" firebase server (use any email, I don't care). If all goes well, you then have an empty screen in front of you. This is because you don't have plants yet. 
Use search to search for a plant, add it with the relevant info and press save (don't kill it just yet, that'll come later). 
If you like your plant enough you can give it a check representing a favourite, which is stored locally on the device.

![mainactivity screenshot](https://github.com/JNorbury/Jonahnorbury-pset6/blob/NEW_PROJ/device-2016-12-16-234635.png?raw=false)

If you are like me and still dehydrate the plant, you can go to its screen and kill it. It will then be removed from your life, virtually.

![showplantactivity](https://github.com/JNorbury/Jonahnorbury-pset6/blob/NEW_PROJ/device-2016-12-16-235126.png?raw=true)

## FAQ
### Why use WIKI, isn't that going to give other results, too?
Yes, it will. I wished to have used a plant-specific api, but many are scientific databases that only store the latin names; working around this was difficult enough so I decided to use a more general API and allow users to do anything with it.

### Did you misspell Plantt?
No, this is a marketing trick. From http://thenextweb.com/entrepreneur/2012/04/22/before-naming-your-startup-read-this/:
"Not only does the name [Digg] remain simple (a repetition of only the last letter), but the repetition happens to be of a consonant whose doubling doesn’t alter the original pronunciation or cause excessive spelling confusion (it’s easy to remember two “g’s” instead of one). Further, the name is short enough to withstand the modification.
