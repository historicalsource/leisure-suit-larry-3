;**
;**	Messages for room 415 & 416 -- mirrored hallways
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 18, 1989
;**


(script#				417)

(public
	regCasino		0
)


(instance regCasino of Region
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said 'play/game')
					(Said 'gamble'))
				(Print "You have no desire to gamble.")
				(Print "(You got enough of that in \"Leisure Suit Larry
					in the Land of the Lounge Lizards!\")" LOW)
			)

			((and debugging (Said 'increase/showroom'))
				(Printf "Ok, Al. Showroom state is now %d, where\n\n
					0 = SRshowIsOn\n
					1 = SRshowDone\n
					2 = SRcherriOnPhone\n
					3 = SRknowsAboutDeed\n
					4 = SRcherriBackstage\n
					5 = SRstageDoorUnlocked\n
					6 = SRdone\n" 
					(++ showroomState))
			)

			((and debugging (Said 'increase/newspaper'))
				(Printf "Ok, Al. Newspaper state is now %d, where\n\n
					0 = NSnotYet\n
					1 = NSpComing\n
					2 = NSshowroom\n
					3 = NSpHere\n
					4 = NSlMissing\n"
					(++ newspaperState))
			)

			((and debugging (Said 'increase/lawyer'))
				(Printf "Ok, Al. Lawyer state is now %d, where\n\n
					0 = LSbusy\n
					1 = LSfree\n
					2 = LSwaiting4deed\n
					3 = LSdeedReady\n
					4 = LSneeds500\n
					5 = LSdivorce\n
					6 = LSwaiting4divorce\n
					7 = LSdivorceReady\n
					8 = LSdone\n"
					(++ lawyerState))
			)

			((Said 'look>')
				(cond
					((Said '/gambler, player, staircase, people, man, woman')
						(Print "You see many tourists donating their
							hard-earned money to \"Natives, Inc.\"
							Being intimately familiar with the profit margins
							of gaming,
							you're glad it's them and not you!")
					)

					((Said '/railing')
						(Print "The railing prevents you from falling off the
							side of this balcony.")
					)

					((Said '/door')
						(Print "The doorway leads out of this scene.
							Wherever could it go?")
					)

					((Said '/mirror')
						(Print ".kcab gnikool flesruoy ees uoY")
						(if (not (TstFlg lookedInMirror))
							(SetFlg lookedInMirror)
							(theGame changeScore 2)
						)
					)

					((Said '[/room]')
						(Print "If you would look in the mirror,
							you would see yourself.")
					)
				)
			)
		)
	)
)
