/*
 * Copyright 2015 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

/**
 * == The OAuth2 auth provider
 *
 * This component contains an out of the box OAuth2 Relay Party implementation. To use this project, add the following
 * dependency to the _dependencies_ section of your build descriptor:
 *
 * * Maven (in your `pom.xml`):
 *
 * [source,xml,subs="+attributes"]
 * ----
 * <dependency>
 *   <groupId>${maven.groupId}</groupId>
 *   <artifactId>${maven.artifactId}</artifactId>
 *   <version>${maven.version}</version>
 * </dependency>
 * ----
 *
 * * Gradle (in your `build.gradle` file):
 *
 * [source,groovy,subs="+attributes"]
 * ----
 * compile '${maven.groupId}:${maven.artifactId}:${maven.version}'
 * ----
 *
 * OAuth2 lets users grant the access to the desired resources to third party applications, giving them the possibility
 * to enable and disable those accesses whenever they want.
 *
 * Vert.x OAuth2 supports the following flows.
 *
 * * Authorization Code Flow (for apps with servers that can store persistent information).
 * * Password Credentials Flow (when previous flow can't be used or during development).
 * * Client Credentials Flow (the client can request an access token using only its client credentials)
 *
 * === Authorization Code Flow
 *
 * The authorization code grant type is used to obtain both access tokens and refresh tokens and is optimized for
 * confidential clients. As a redirection-based flow, the client must be capable of interacting with the resource
 * owner's user-agent (typically a web browser) and capable of receiving incoming requests (via redirection) from the
 * authorization server.
 *
 * For more details see http://tools.ietf.org/html/draft-ietf-oauth-v2-31#section-4.1[Oauth2 specification, section 4.1].
 *
 * === Password Credentials Flow
 *
 * The resource owner password credentials grant type is suitable in cases where the resource owner has a trust
 * relationship with the client, such as the device operating system or a highly privileged application. The
 * authorization server should take special care when enabling this grant type, and only allow it when other flows are
 * not viable.
 *
 * The grant type is suitable for clients capable of obtaining the resource owner's credentials (username and password,
 * typically using an interactive form).  It is also used to migrate existing clients using direct authentication
 * schemes such as HTTP Basic or Digest authentication to OAuth by converting the stored credentials to an access token.
 *
 * For more details see http://tools.ietf.org/html/draft-ietf-oauth-v2-31#section-4.3[Oauth2 specification, section 4.3].
 *
 * === Client Credentials Flow
 *
 * The client can request an access token using only its client credentials (or other supported means of authentication)
 * when the client is requesting access to the protected resources under its control, or those of another resource owner
 * that have been previously arranged with the authorization server (the method of which is beyond the scope of this
 * specification).
 *
 * The client credentials grant type MUST only be used by confidential clients.
 *
 * For more details see http://tools.ietf.org/html/draft-ietf-oauth-v2-31#section-4.4[Oauth2 specification, section 4.4].
 *
 * === Extensions
 *
 * The provider supports RFC7523 an extension to allow server to server authorization based on JWT.
 *
 * === Getting Started
 *
 * An example on how to use this provider and authenticate with GitHub can be implemented as:
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example1}
 * ----
 *
 * ==== Authorization Code flow
 *
 * The Authorization Code flow is made up from two parts. At first your application asks to the user the permission to
 * access their data. If the user approves the OAuth2 server sends to the client an authorization code. In the second
 * part, the client POST the authorization code along with its client secret to the authority server in order to get the
 * access token.
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example2}
 * ----
 *
 * ==== Password Credentials Flow
 *
 * This flow is suitable when the resource owner has a trust relationship with the client, such as its computer
 * operating system or a highly privileged application. Use this flow only when other flows are not viable or when you
 * need a fast way to test your application.
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example3}
 * ----
 *
 * ==== Client Credentials Flow
 *
 * This flow is suitable when client is requesting access to the protected resources under its control.
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example4}
 * ----
 *
 * === AccessToken object
 *
 * When a token expires we need to refresh it. OAuth2 offers the AccessToken class that add a couple of useful methods
 * to refresh the access token when it is expired.
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example5}
 * ----
 *
 * When you've done with the token or you want to log out, you can revoke the access token and refresh token.
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example6}
 * ----
 *
 * === Example configuration for common OAuth2 providers
 *
 * For convenience there are several helpers to assist your with your configuration. Currently we provide:
 *
 * * Azure Active Directory {@link io.vertx.ext.auth.oauth2.providers.AzureADAuth}
 * * Box.com {@link io.vertx.ext.auth.oauth2.providers.BoxAuth}
 * * Dropbox {@link io.vertx.ext.auth.oauth2.providers.DropboxAuth}
 * * Facebook {@link io.vertx.ext.auth.oauth2.providers.FacebookAuth}
 * * Foursquare {@link io.vertx.ext.auth.oauth2.providers.FoursquareAuth}
 * * Github {@link io.vertx.ext.auth.oauth2.providers.GithubAuth}
 * * Google {@link io.vertx.ext.auth.oauth2.providers.GoogleAuth}
 * * Instagram {@link io.vertx.ext.auth.oauth2.providers.InstagramAuth}
 * * Keycloak {@link io.vertx.ext.auth.oauth2.providers.KeycloakAuth}
 * * LinkedIn {@link io.vertx.ext.auth.oauth2.providers.LinkedInAuth}
 * * Mailchimp {@link io.vertx.ext.auth.oauth2.providers.MailchimpAuth}
 * * Salesforce {@link io.vertx.ext.auth.oauth2.providers.SalesforceAuth}
 * * Shopify {@link io.vertx.ext.auth.oauth2.providers.ShopifyAuth}
 * * Soundcloud {@link io.vertx.ext.auth.oauth2.providers.SoundcloudAuth}
 * * Stripe {@link io.vertx.ext.auth.oauth2.providers.StripeAuth}
 * * Twitter {@link io.vertx.ext.auth.oauth2.providers.TwitterAuth}
 *
 * ==== JBoss Keycloak
 *
 * When using this Keycloak the provider has knowledge on how to parse access tokens and extract grants from inside.
 * This information is quite valuable since it allows to do authorization at the API level, for example:
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example13}
 * ----
 *
 * We also provide a helper class for Keycloak so that we can we can easily retrieve decoded token and some necessary
 * data (e.g. `preferred_username`) from the Keycloak principal. For example:
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example14}
 * ----
 *
 * ==== Google Server to Server
 *
 * The provider also supports Server to Server or the RFC7523 extension. This is a feature present on Google with their
 * service account.
 *
 * === Token Introspection
 *
 * Tokens can be introspected in order to assert that they are still valid. Although there is RFC7660 for this purpose
 * not many providers implement it. Instead there are variations also known as `TokenInfo` end points. The OAuth2
 * provider will accept both end points as a configuration. Currently we are known to work with `Google` and `Keycloak`.
 *
 * Token introspection assumes that tokens are opaque, so they need to be validated on the provider server. Every time a
 * token is validated it requires a round trip to the provider. Introspection can be performed at the OAuth2 level or at
 * the User level:
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example15}
 * ----
 *
 * === Verifying JWT tokens
 *
 * We've just covered how to introspect a token however when dealing with JWT tokens one can reduce the amount of trips
 * to the provider server thus enhancing your overall response times. In this case tokens will be verified using the
 * JWT protocol at your application side only. Verifying JWT tokens is cheaper and offers better performance, however
 * due to the stateless nature of JWTs it is not possible to know if a user is logged out and a token is invalid. For
 * this specific case one needs to use the token introspection if the provider supports it.
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example16}
 * ----
 *
 * Until now we covered mostly authentication, although the implementation is relay party (that means that the real
 * authentication happens somewhere else), there is more you can do with the handler. For example you can also do
 * authorization if the provider is known to support JSON web tokens. This is a common feature if your provider is a
 * OpenId Connect provider or if the provider does support `access_token`s as JWTs.
 *
 * Such provider is Keycloak that is a OpenId Connect implementation. In that case you will be able to perform
 * authorization in a very easy way.
 *
 * == Authorization with JWT tokens
 *
 * Given that Keycloak does provide `JWT` `access_token`s one can authorize at two distinct levels:
 *
 * * role
 * * authority
 *
 * To distinct the two, the auth provider follows the same recommendations from the base user class, i.e.: use the`:` as
 * a separator for the two. It should be noted that both role and authorities do not need to be together, in the most
 * simple case an authority is enough.
 *
 * In order to map to keycloak's token format the following checks are performed:
 *
 * 1. If no role is provided, it is assumed to the the provider realm name
 * 2. If the role is `realm` then the lookup happens in `realm_access` list
 * 3. If a role is provided then the lookup happends in the `resource_access` list under the role name
 *
 * === Check for a specific authorities
 *
 * Here is one example how you can perform authorization after the user has been loaded from the oauth2 handshake, for
 * example you want to see if the user can `print` in the current application:
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example17}
 * ----
 *
 * However this is quite specific, you might want to verify if the user can `add-user` to the whole system (the realm):
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example18}
 * ----
 *
 * Or if the user can access the `year-report` in the `finance` department:
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example19}
 * ----
 *
 * == Token Management
 *
 * === Check if it is expired
 *
 * Tokens are usually fetched from the server and cached, in this case when used later they might have already expired
 * and be invalid, you can verify if the token is still valid like this:
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example21}
 * ----
 *
 * This call is totally offline, it could still happen that the Oauth2 server invalidated your token but you get a non
 * expired token result. The reason behind this is that the expiration is checked against the token expiration dates,
 * not before date and such values.
 *
 * === Refresh token
 *
 * There are times you know the token is about to expire and would like to avoid to redirect the user again to the login
 * screen. In this case you can refresh the token. To refresh a token you need to have already a user and call:
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example22}
 * ----
 *
 * === Revoke token
 *
 * Since tokens can be shared across various applications you might want to disallow the usage of the current token by
 * any application. In order to do this one needs to revoke the token against the Oauth2 server:
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example23}
 * ----
 *
 * It is important to note that this call requires a token type. The reason is because some providers will return more
 * than one token e.g.:
 *
 * * id_token
 * * refresh_token
 * * access_token
 *
 * So one needs to know what token to invalidate. It should be obvious that if you invalidate the `refresh_token` you're
 * still logged in but you won't be able to refresh anymore, which means that once the token expires you need to redirect
 * the user again to the login page.
 *
 * === Introspect
 *
 * Introspect a token is similar to a expiration check, however one needs to note that this check is fully online. This
 * means that the check happens on the OAuth2 server.
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example24}
 * ----
 *
 * Important note is that even if the `expired()` call is `true` the return from the `introspect` call can still be an
 * error. This is because the OAuth2 might have received a request to invalidate the token or a loggout in between.
 *
 * === Logging out
 *
 * Logging out is not a `Oauth2` feature but it is present on `OpenID Connect` and most providers do support some sort
 * of logging out. This provider also covers this area if the configuration is enough to let it make the call. For the
 * user this is as simple as:
 *
 * [source,$lang]
 * ----
 * {@link examples.AuthOAuth2Examples#example20}
 * ----
 *
 */
@Document(fileName = "index.adoc")
@ModuleGen(name = "vertx-auth-oauth2", groupPackage = "io.vertx")
package io.vertx.ext.auth.oauth2;

import io.vertx.codegen.annotations.ModuleGen;
import io.vertx.docgen.Document;
