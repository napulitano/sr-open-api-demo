# Demo App that uses SR OPEN API (https://sverigesradio.se/oppetapi)

Deployed at: https://warm-shelf-75689.herokuapp.com/

# Endpoint
* https://warm-shelf-75689.herokuapp.com/ - Demo Page
* https://warm-shelf-75689.herokuapp.com/playlist?channelId=224 - Get playlist by channelId, startDate, endDate

## Request params
- channelId : channel id you can get from demo page
- start : start date for your search, must be in format yyyy-MM-dd
- end. : end date for your search, must be in format yyyy-MM-dd


# How to run locally?

* checkout the repo from git or download as zip
* via you favorite terminal go inside the directory where you checkout or unzipped the repo
* run: mvn clean install
* start with:  mvn spring-boot:run
