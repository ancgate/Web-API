"use strict";var precacheConfig=[["./index.html","3b7ff9fb9d3db18c58a7c932bd203cf4"],["./static/css/main.23de7225.css","fbca4e005802a54ae8f934190f1542cb"],["./static/js/0.de130e8f.chunk.js","df27907205648fb2a7a2d3fb526d2518"],["./static/js/1.504e3cc4.chunk.js","8892c1557a850118e80e84664f440317"],["./static/js/10.68d5b267.chunk.js","2f914d58986751dabf11827841882142"],["./static/js/11.cc286fd5.chunk.js","038d00e8706e56bc2540c72ac3acacb4"],["./static/js/12.e040fdce.chunk.js","c76eae77b0fc387e367632189ef8c104"],["./static/js/13.6935cc01.chunk.js","308ee0b89325f171841152ed1317f2a3"],["./static/js/14.c52bcb57.chunk.js","472fdba028764922c4b65498d020fdd2"],["./static/js/15.074dea68.chunk.js","5bd268b48271ec81fab1dcb3fb4e2a1a"],["./static/js/16.7deaed69.chunk.js","d9fb310c5b6ec47625ea0a06d6fe913d"],["./static/js/17.90dd5193.chunk.js","e78f02621c2c82c55c368b26a129e216"],["./static/js/18.a7d0e732.chunk.js","ebb1f775c8b2c3695b6b06f807e47529"],["./static/js/19.05722b54.chunk.js","aafc11cac01a656af839a687bce0fa6f"],["./static/js/2.b57cffda.chunk.js","508541882157ec23d6963b9ee2c27b22"],["./static/js/3.cd683335.chunk.js","575c0f8a599722d24af695f9efafe3c9"],["./static/js/4.770807a6.chunk.js","eb8c60ece22707d32c1b4e9acb64e0a4"],["./static/js/5.02bb2768.chunk.js","d770139482182d8b57ceac08b1b6b878"],["./static/js/6.11778f88.chunk.js","db385f66c229b40166a581ed1e285de6"],["./static/js/7.3c178b62.chunk.js","e664599141d30ffa98b74a4641124313"],["./static/js/8.b5fca2b2.chunk.js","2aa1dcbaeb81f19a190a70d3dd2fb43f"],["./static/js/9.990f945a.chunk.js","5625ee16d6659ad5161e61fac73444a4"],["./static/js/main.75aa0fd3.js","2644f7325b731c6c92150f281a57f3a8"],["./static/media/jsoneditor-icons.bfab7b16.svg","bfab7b16cb24ac5e2856e2b172f47fe8"],["./static/media/logo.db6c2609.png","db6c26096be508125b12312f4fd54ad5"]],cacheName="sw-precache-v3-sw-precache-webpack-plugin-"+(self.registration?self.registration.scope:""),ignoreUrlParametersMatching=[/^utm_/],addDirectoryIndex=function(e,t){var a=new URL(e);return"/"===a.pathname.slice(-1)&&(a.pathname+=t),a.toString()},cleanResponse=function(t){return t.redirected?("body"in t?Promise.resolve(t.body):t.blob()).then(function(e){return new Response(e,{headers:t.headers,status:t.status,statusText:t.statusText})}):Promise.resolve(t)},createCacheKey=function(e,t,a,c){var n=new URL(e);return c&&n.pathname.match(c)||(n.search+=(n.search?"&":"")+encodeURIComponent(t)+"="+encodeURIComponent(a)),n.toString()},isPathWhitelisted=function(e,t){if(0===e.length)return!0;var a=new URL(t).pathname;return e.some(function(e){return a.match(e)})},stripIgnoredUrlParameters=function(e,a){var t=new URL(e);return t.hash="",t.search=t.search.slice(1).split("&").map(function(e){return e.split("=")}).filter(function(t){return a.every(function(e){return!e.test(t[0])})}).map(function(e){return e.join("=")}).join("&"),t.toString()},hashParamName="_sw-precache",urlsToCacheKeys=new Map(precacheConfig.map(function(e){var t=e[0],a=e[1],c=new URL(t,self.location),n=createCacheKey(c,hashParamName,a,/\.\w{8}\./);return[c.toString(),n]}));function setOfCachedUrls(e){return e.keys().then(function(e){return e.map(function(e){return e.url})}).then(function(e){return new Set(e)})}self.addEventListener("install",function(e){e.waitUntil(caches.open(cacheName).then(function(c){return setOfCachedUrls(c).then(function(a){return Promise.all(Array.from(urlsToCacheKeys.values()).map(function(t){if(!a.has(t)){var e=new Request(t,{credentials:"same-origin"});return fetch(e).then(function(e){if(!e.ok)throw new Error("Request for "+t+" returned a response with status "+e.status);return cleanResponse(e).then(function(e){return c.put(t,e)})})}}))})}).then(function(){return self.skipWaiting()}))}),self.addEventListener("activate",function(e){var a=new Set(urlsToCacheKeys.values());e.waitUntil(caches.open(cacheName).then(function(t){return t.keys().then(function(e){return Promise.all(e.map(function(e){if(!a.has(e.url))return t.delete(e)}))})}).then(function(){return self.clients.claim()}))}),self.addEventListener("fetch",function(t){if("GET"===t.request.method){var e,a=stripIgnoredUrlParameters(t.request.url,ignoreUrlParametersMatching),c="index.html";(e=urlsToCacheKeys.has(a))||(a=addDirectoryIndex(a,c),e=urlsToCacheKeys.has(a));var n="./index.html";!e&&"navigate"===t.request.mode&&isPathWhitelisted(["^(?!\\/__).*"],t.request.url)&&(a=new URL(n,self.location).toString(),e=urlsToCacheKeys.has(a)),e&&t.respondWith(caches.open(cacheName).then(function(e){return e.match(urlsToCacheKeys.get(a)).then(function(e){if(e)return e;throw Error("The cached response that was expected is missing.")})}).catch(function(e){return console.warn('Couldn\'t serve response for "%s" from cache: %O',t.request.url,e),fetch(t.request)}))}});