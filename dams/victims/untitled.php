<?php

use Cake\Console\ConsoleIo;
use Cake\Log\Log;
use Cake\ORM\TableRegistry;
use Phinx\Migration\AbstractMigration;

class AddSurveyColumnInVendorsUsers extends AbstractMigration
{
    /**
     * Change Method.
     *
     * Write your reversible migrations using this method.
     *
     * More information on writing migrations is available here:
     * http://docs.phinx.org/en/latest/migrations.html#the-abstractmigration-class
     *
     * The following commands can be used in this method and Phinx will
     * automatically reverse them when rolling back:
     *
     *    createTable
     *    renameTable
     *    addColumn
     *    renameColumn
     *    addIndex
     *    addForeignKey
     *
     * Remember to call "create()" or "update()" and NOT "save()" when working
     * with the Table class.
     */
    public function change()
    {
        Log::info("Adding survey_id column to vendors_users table", 'migration');
        $table = $this->table('vendors_users');
        $table->addColumn('survey_id', 'integer', [
            'default' => null,
            'null' => true
        ])
        ->update();

        Log::info("Column survey_id added to vendors_users table", 'migration');
        Log::info("Adding survey_id data for vendors_users", 'migration');
        $vendorsUsersTable = TableRegistry::get('VendorsUsers');
        $vendorsUsers = $vendorsUsersTable->find()
            ->select(['id', 'user_id', 'email' => 'Users.email'])
            ->join([
                'Users' => [
                    'table' => 'users',
                    'type' => 'INNER',
                    'conditions' => 'VendorsUsers.user_id = Users.id'
                ]
            ])
            ->where(['user_id IS NOT NULL and vendor_id != 1 and email like "%\_%\_%"'])
            ->hydrate(false)
            ->toArray();

        foreach ($vendorsUsers as $vendorUser) {
            if (substr_count($vendorUser['email'], "_") == 2) {
                $emailDetails = explode("@", $vendorUser['email']);
                $uniqueParameter = explode("_", $emailDetails[0]);
                $surveyId = $uniqueParameter[2];
                $vendorUser = $vendorsUsersTable->get($vendorUser['id']);
                $vendorUser['survey_id'] = $surveyId;
                $status = $vendorsUsersTable->save($vendorUser);
                if (!$status) {
                    Log::error("Adding survey_id to the vendors_users failed.");

                    return;
                }
            }
        }
        Log::info("survey_id is added to vendors_users successfully.", 'migration');
    }
}
