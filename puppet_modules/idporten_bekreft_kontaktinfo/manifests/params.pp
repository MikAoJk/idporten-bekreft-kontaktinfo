#params.pp
class idporten_bekreft_kontaktinfo::params {
  # Note - need to update package.json & rebuild if context path is changed
  $app_context_path                            = '/'
  $install_dir                                     = '/opt/'
  $config_dir                                     = '/etc/opt/'
  $config_root                                     = '/etc/opt/'
  $log_root                                        = '/var/log/'
  $log_level                                       = 'WARN'
  $application                                 = 'idporten-bekreft-kontaktinfo'
  $service_name                                = 'idporten-bekreft-kontaktinfo'
  $artifact_id                                 = 'idporten-bekreft-kontaktinfo'
  $group_id                                    = 'no.digdir'

  $oidc_provider_url            = hiera('idporten_bekreft_kontaktinfo::idporten_oidc_provider_url')

  $server_port                                 = 8080
  $java_home                                     = hiera('platform::java_home')
}
