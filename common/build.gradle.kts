

tasks{
    withType<Jar>{
        enabled = true
    }
    bootJar {
        enabled = false
    }

    bootBuildImage{
        enabled = false
    }
}
