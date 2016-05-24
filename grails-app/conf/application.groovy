hibernate {
    cache {
        queries = false
        use_second_level_cache = true
        use_query_cache = false
        region {
            factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory'
        }
    }
}

server {
    port = 9001
}

dataSource {
    pooled = true
    jmxExport = true
    driverClassName = 'org.h2.Driver'
    username = 'sa'
    password = ''
}

environments {
    development {
        dataSource {
            dbCreate = 'create-drop'
            url = 'jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE'
        }
        test {
            dataSource {
                dbCreate = 'update'
                url = 'jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE'
            }
        }
        production {
            dataSource {
                dbCreate = 'update'
                url = 'jdbc:h2:./prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE'
                properties {
                    jmxEnabled = true
                    initialSize = 5
                    maxActive = 50
                    minIdle = 5
                    maxIdle = 25
                    maxWait = 10000
                    maxAge = 600000
                    timeBetweenEvictionRunsMillis = 5000
                    minEvictableIdleTimeMillis = 60000
                    validationQuery = 'SELECT 1'
                    validationQueryTimeout = 3
                    validationInterval = 15000
                    testOnBorrow = true
                    testWhileIdle = true
                    testOnReturn = false
                    jdbcInterceptors = 'ConnectionState'
                    defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
                }
            }
        }
    }
}

root.image.url = '/var/images'

grails {
    profile = 'web'

    codegen {
        defaultPackage = 'grails3'
    }

    spring {
        transactionManagement {
            proxies = 'false'
        }
    }
}

info {
    app {
        name = '@info.app.name@'
        version = '@info.app.version@'
        grailsVersion = '@info.app.grailsVersion@'
    }
}

spring {

    groovy {
        template['check-template-location'] = false
    }
}

grails {
    mime {
        disable {
            accept {
                header {
                    userAgents:
                    ['Gecko', 'WebKit', 'Presto', 'Trident']
                }
            }
        }

        types {
            all = '*/*'
            atom = 'application/atom+xml'
            css = 'text/css'
            csv = 'text/csv'
            form = 'application/x-www-form-urlencoded'
            html = ['text/html', 'application/xhtml+xml']
            js = 'text/javascript'
            json = ['application/json', 'text/json']
            multipartForm = 'multipart/form-data'
            pdf = 'application/pdf'
            rss = 'application/rss+xml'
            text = 'text/plain'
            hal = ['application/hal+json', 'application/hal+xml']
            xml = ['text/xml', 'application/xml']
        }
    }

    urlmapping {
        cache {
            maxsize = 1000
        }
    }

    controllers {
        defaultScope = 'singleton'
    }

    converters {
        encoding = 'UTF-8'
    }

    views {
        'default' {
            codec = 'html'
        }

        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml'
            codecs {
                expression = 'html'
                scriptlets = 'html'
                taglib = 'none'
                staticparts = 'none'
            }
        }
    }
}

endpoints {
    jmx['unique - names'] = true
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.grails3Tutorial.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.grails3Tutorial.security.UserRole'
grails.plugin.springsecurity.authority.className = 'com.grails3Tutorial.security.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/', access: ['permitAll']],
        [pattern: '/error', access: ['permitAll']],
        [pattern: '/index', access: ['permitAll']],
        [pattern: '/index.gsp', access: ['permitAll']],
        [pattern: '/shutdown', access: ['permitAll']],
        [pattern: '/assets/**', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']],
        [pattern: '/**Admin/**', access: ['ROLE_ADMIN']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**', filters: 'none'],
        [pattern: '/**/js/**', filters: 'none'],
        [pattern: '/**/css/**', filters: 'none'],
        [pattern: '/**/images/**', filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**', filters: 'JOINED_FILTERS']
]

environments {
    development {
        // Enable DB console access for development purposes
        grails.dbconsole.enabled = true
        grails.dbconsole.urlRoot = '/admin/dbconsole'

        grails.plugin.springsecurity.controllerAnnotations.staticRules.addAll(
                [[pattern: '/admin/dbconsole/**', access: ['permitAll']],
                ])
    }
    production {
        // Enable DB console access for development purposes
        grails.dbconsole.enabled = true
        grails.dbconsole.urlRoot = '/admin/dbconsole'

        grails.plugin.springsecurity.controllerAnnotations.staticRules.addAll(
                [[pattern: '/admin/dbconsole/**', access: ['ROLE_ADMIN']]]
        )
    }
}