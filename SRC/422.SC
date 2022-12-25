;**
;**	Logics for script 422 -- Cherri's script
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 17, 1990
;**


(script#				422)

(public
	CherriScript	0
)

(include "420.sh")


(synonyms
	(girl cherri dancer showgirl )
)


(enum												;**	CherriScript state values
	Cintro
	goingTo425
	changeRooms
	startCbackstage
	CthanksL
	CisLeaving
	CatDoor
	CdoorIsOpen
	CcloseDoor
	CisBackstage
	Cwaits4entrance
	CopensDoor
	doorOpenCcomingOut
	closeDoorExitingBackstage
	fromBSdoorClosedAgain
	CherriATphone
)


(instance CherriScript of Script
	(method (doit)
		(super doit)

		(if (and (== state Cwaits4entrance)
					( > (ego x) 280))
			(self cue)			
		)

		(if (and (== showroomState SRcherriOnPhone)
					(== (client loop) lTalkingPhone)
					(== (client x) onPhoneX)
					(== (client y) cherriY))
			(switch (Random 0 6)
				(0
					(client setCel 0)
				)
				(1
					(client setCycle Forward)
				)
			)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) 
					(!= showroomState SRcherriOnPhone)	;**	only while at phone
					(event claimed)) 
			(return)
		)

		(cond
			((or	(Said 'give/cherri')
					(Said 'give/anyword/cherri'))
				(Print "Perhaps you should look at her first.")
			)

			((Said 'look/cherri')
				(cond
					((!= showroomState SRcherriOnPhone)
						(Print "Where?")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					((!= (client xLast) (client x))
						(Print "You're too late. She's leaving.")
					)
		   		((not nearPhone)
		   			(Print "A beautiful woman is here,
							talking on the telephone.
							Perhaps you should walk over to her.")
		   		)
					(else
						(self changeState goingTo425)
					)
				)
			)

			((and (== showroomState SRcherriOnPhone)	;**	only while at phone
					(Said '/cherri'))
				(Print "Perhaps you should establish eye contact first.")
			)
		)
	)


	(method (changeState newState)
		(ShowState self newState 3 green)

		(switch (= state newState)

			(Cintro
				(if (== showroomState SRknowsAboutDeed)
					(self changeState startCbackstage)
				)

				(if (== showroomState SRshowDone)
					(self changeState Cwaits4entrance)					
					(client
						posn				-20 143 ,
						stopUpd,
					)
				)
			)


			;**	Handle L looking at C

			(goingTo425
				(Ok)
				(HandsOff)
				(= egoState GOING_TO_BIG_FACE)
				(Printf "\"Hello, beautiful. I loved your dancing,\" 
					you say to the gorgeous woman in the dressing gown. %s" laffer)
				(client
					setStep			0 0,
					setMotion		Follow ego 222,	;**	just face Larry, ok?
				)
				(delay 3)
			)

			(changeRooms
				(if (not (TstFlg seenCherri))
					(SetFlg seenCherri)
					(theGame changeScore 5)
				)
				(Print "\"Hello yourself, Larry,\" replies the bathrobed beauty,
					turning to face you. \"I'm Cherri Tart.\"")
				(= prevRoomX (ego x))
				(= prevRoomY (ego y))
				(= prevLoop	 (ego loop))
				(curRoom newRoom 435)
			)

			
			;**		Cherri's had it; she's given her clue and is heading for
			;**	backstage.

			(startCbackstage
				(HandsOff)
				(delay 3)
			)

			(CthanksL
				(Print "\"I've certainly enjoyed our little chat together,
					Larry, and I'll look forward to hearing from you again
					soon! Now, if you'll pardon me, I must head backstage.
					Our stage manager has such a short fuse for a guy
					with such a sour disposition!\"")
				(delay 3)
			)
			
			(CisLeaving
				(Print "\"When you get the deed to the land,
					just knock softly on that door over there.
					I'll be waiting near the door. Till then.\"")
				(client
					illegalBits		0,
					ignoreActors	FALSE,
					setLoop			facingWest,
					setCycle			Walk,
					setMotion		MoveTo outsideStageDoorPosn self,
				)
				(if (and	( > (ego y) (client y))
							( < (ego x) (+ (client x) 15)))
					(ego
						setCycle			Walk,
						setMotion		MoveTo (+ onPhoneX 15) (ego y) ,
					)
				)
			)

			(CatDoor
				(someObject
					setCycle			EndLoop self, 
				)
			)

			(CdoorIsOpen
				(Print "\"Oh, Larry,\" Cherri cries,
					\"I can just picture my little country place now!\"")
				(someObject			stopUpd)
				(client
					setMotion		MoveTo -20 140 self,
				)
			)

			(CcloseDoor
				(someObject
					setCycle			BegLoop self,
				)
			)

			(CisBackstage
				(soundFX
					number			sDoorClosing,
					loop				1 ,
					play ,			
				)
				(= showroomState SRcherriBackstage)
				(someObject			stopUpd)
				(NormalEgo)
				(ego
					observeControls	cYellow cLtMagenta,
				)
				(client dispose)
				(self dispose)
			)


			;**	This is what brings C out from backstage to the phone...

			(Cwaits4entrance	
				(delay 15)
			)

			(CopensDoor
				(if ( < (ego x) 160)
					(-- state)
					(= cycles 2)				;**	keep trying until L moves East
				else
					(HandsOff)
					(someObject
						setCycle			EndLoop self, 
					)
					(delay 0)
				)
			)

			(doorOpenCcomingOut
				(someObject			stopUpd)
				(client
					posn				insideStageDoorPosn,
					loop				facingEast,
					illegalBits		0,
					setCycle			Walk,
					setMotion		MoveTo outsideStageDoorPosn self,
				)
			)

			(closeDoorExitingBackstage
				(someObject
					setCycle			BegLoop self,
				)
			)

			(fromBSdoorClosedAgain
				(soundFX
					number			sDoorClosing,
					loop				1 ,
					play ,			
				)
				(someObject			stopUpd)
				(client
					setMotion		MoveTo onPhoneX cherriY self,
				)
			)

			(CherriATphone
				(client
					loop				lTalkingPhone,
				)
				(= showroomState SRcherriOnPhone)
				(HandsOn)
				(ego
					observeControls	cYellow cLtMagenta,
				)
			)
		)
	)
)

