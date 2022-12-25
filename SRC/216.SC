;**
;**	Logics for room 216 -- "Little Dutch Boy"
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 26, 1989
;**


(script#				216)

(public
	rm216				0
)


(procedure
	PrintKalalau
	Print216
)


(define	vRoom							216)
	(define	lMailBox				0)
		(define	cBoxClosed	0)
		(define	cFull			1)
		(define	cEmpty		2)

(define	vLroom				(+ 1 vRoom))
	(define	lLopeningMailbox	0)
	(define	lLgettingMail		1)
	(define	lLholdingUpMail	2)
	(define	lMailIcon			3)

(define	vK&BBinHotTub				218)
	(define	lKtalking			0)
	(define	lBBtalking			1)
	(define	lMuffDiving			2)
	(define	lCaressing			0)
	(define	lBubbles				0)

(define	vProgrammedBy				219)
	(define	lProgrammedBy		0)
	(define	lAl					1)
	(define	lCarlos				2)


(define sBubbles						216)
(define sMailboxOpening				217)	
(define sMailboxClosing				218)	


(define nearMailBox 			(& (ego onControl) cBlue))
(define nearFence				(ego inRect 34 114 100 134))
(define EGO_AT_BOX			118 120)
(define BOX_X						134)
(define BOX_Y						107)


(synonyms
	(box lid )
)


(enum												;**	RoomScript state values
	intro
	learnThePlot
	readMsg2
	readMsg3
	insert3
	readMsg4
	readMsg5
	readMsg6
	readMsg7
	readMsg8
	readMsg9
	plotDone
	openBox
	openDelay
	openBox3
	lowerYourArm
	boxIsOpen
	even
	closeBox
	closeDelay
	closeBox3
	shutUpYourBox
	boxIsClosed
	getEnvelope
	envelopeDelay
	reachIntoBox
	getEnvelope3
	holdUp
	eagerly
	raiseEnvelope
	raiseEnvelope2
	putEnvelopeDown
	downDelay
	envelopeIsOpen
	allDone
)


(enum												;**	CreditsScript state values
	doCredits
	doCredits1
	doCredits2
	doCredits3
	doCredits4
	doCredits5
	clearCredits
)


(enum												;**	KandBBScript state values
	KandBBinit
	makeKtalk
	stopKtalk
	makeBBtalk
	stopBBtalk
	makeBBdive
	riseForAir
)


(local
	girlTalkCount
	boxOpen
	[string 222]
)


(instance rm216 of Room
	(properties
		picture		216
		east			210
	)

	(method (init)
		(if (not (TstFlg beenIn216))
			(aKandBB			init)
		)

		(Load VIEW vLroom)

		(Load SOUND sMailboxOpening)
		(Load SOUND sMailboxClosing)	

		(super init)

		(aMailBox
			init,
			stopUpd,
		)

		(self
			setScript 		RoomScript
		)

		(if (and	(TstFlg beenIn216)
					(not (TstFlg sawCredits216)))
			(Load VIEW vProgrammedBy)
			(aCredit1			init)
			(aCredit2			init)
		)

		(NormalEgo)
		(ego
			loop				facingLeft,
		 	posn				318 166,
			observeBlocks	blockFence1 blockFence2,
			init
		)
		(if (cast contains aKandBB)
			(soundFX
				number		sBubbles,
				loop			FOREVER,
				play,
			)
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event)
		(if (and (not (super handleEvent: event))
					(not (event claimed))
					modelessDialog
					(== (event message) ENTER)
					(== (event type) keyDown))
			(event claimed TRUE)
			(cls)
			(self cue)
		)

		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'climb, jump/wall')
				(if egoIsPatti
			 		(Print "Larry built these walls a year ago 
			 			to keep out undesirables.")
				else
			 		(Print "A year ago you built these walls
			 			to keep out undesirables; and now you are one.")
				)
			)

			((Said '/spa, tub')
				(if egoIsPatti
					(Print "Can't you just picture you and Larry 
						in that tub?")
				else
					(Print "You fondly recall your many positive experiences
						in that tub.")
				)
			)

			((Said 'get/box')
			 	(Print "You can't; it's bolted securely to the wall.")
			)

			((Said 'open/gate')
			 	(Print "You can't; it's securely locked.")
			)

			((Said 'pick, break/lock, gate, fence')
				(Print "There's just no getting in there!")
			)

			((Said 'climb/gate')
				(if egoIsPatti
			 		(Print "That's ONE idea; but not a very good one!")
				else
			 		(Print "A good idea, but there is nothing inside that gate
						for you but bad memories!")
				)
			)

			((Said 'unlock/gate')
			 	(Print "You can't; Kalalau changed the locks!")
			)

			((Said 'look<in/box')
				(cond
					(egoIsPatti
						(Print "You don't need to do that.")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearMailBox)
			 			(NotClose)
					)
					((not boxOpen)
						(Print "Although you try as hard as you can,
							you just can't see through the lid, Larry!")
					)
					((InRoom iCreditCard)
						(Print "Well, well. Look at this!
							It's an envelope from your credit card company.")
					)
					(else
						(Print "Careful examination of the contents
							of your mailbox reveals...")
						(Print "...nothing!")
					)
				)
			)

			((Said 'open/box')
				(cond
					(egoIsPatti
						(Print "You don't need to do that.")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearMailBox)
			 			(NotClose)
					)
					(boxOpen
						(ItIs)
					)
					(else
				 		(self changeState openBox)
					)
				)
			)

			((Said 'close/box')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearMailBox)
			 			(NotClose)
					)
					((not boxOpen)
						(ItIs)
					)
					(else
				 		(self changeState closeBox)
					)
				)
			)

			((Said 'get/card, letter, mail, envelope')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (InRoom iCreditCard))
						(Print "You don't see that here.")
					)
					((not (& (ego onControl) cBlue))
						(NotClose)
					)
					((not boxOpen)
						(Print "Where?")
					)
					(else
						(self changeState getEnvelope)
					)
				)
			)

			((Said 'tickle/finial')
				(Print "You've been playing \"King's Quest IV\" too long!")
			)

			((Said '/wall, fence>')
				(cond
					((Said 'climb/')
						(Print "You COULD do that, but there is no game over there!")
					)
					((Said 'look/')
						(if egoIsPatti
							(Print "High stucco walls lead down a pathway to 
								Larry's former residence, 
								the most beautiful home on Nontoonyt.")
						else
							(Print "High stucco walls lead down a pathway to your
								former residence, the most beautiful home on Nontoonyt.
								Your mailbox hangs on the wall near the gate.")
						)
					)
					(else
						(event claimed TRUE)
						(Print "This fence effectively keeps out all intruders_--
							including you!")
					)
				)
			)

			((Said 'look>')
				(cond
					((and	(InRoom iCreditCard)
							(Said '/mail, letter, envelope, card'))
						(Print "What's that in your mailbox?
							Perhaps you should look in there.")
					)

					((Said '/box')
				 		(if nearMailBox
				 			(Print "Yes. It is a mailbox.")
				 		else
				 			(Print "There's a mailbox next to the gate.")
				 		)
					)

					((Said '/gate')
						(Print "Graceful finials adorn the locked gate.
							What's that?, you say.
							Well, suffice it to say, it looks like a
							big upside-down punching bag
							mounted atop the column beside the gate.
							See, you learn something new every day!")
					)

					((Said '/finial')
						(Print "Yes, that's a big finial, all right!")
						(Print "(It reminds you of a uvula!)" LOW)
					)

					((Said '/house')
						(if egoIsPatti
							(Print "This is where your Larry used to live with
								some cheap hussy.")
						else
							(Print "You were so sure you would spend the rest of
								your life living happily with your beloved Kalalau,
								but, alas, some things are just not meant to be.")
						)
					)

					((Said '[/room]')
						(if egoIsPatti
							(Print "This is where your Larry used to live with
								some cheap hussy.")
						else
							(Print "You gave Kalalau full rein in the design
								and decorating of your home.
								Lucky for you she was the daughter of a king!")
							(Print "(Kalalau was heavily into the \"Grocery Store
								Check-Out Line Magazine\"
								school of architecture!)" LOW)
						)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
				(if (not (TstFlg beenIn216))
					(= cycles 40)			;**	trip only if it's his first time
				)
			)


			;**	Plot exposition: first time into room, only.

			(learnThePlot
				(HandsOff)
				(ego
					loop			facingNorth,
				)
				(Format @string "\"Oh, Kalalau, baby!\" you shout.
					\"Your lover boy Larry is home!\"" )
				(= seconds (Print216))
				(= cycles 0)
			)

			(readMsg2
				(Format @string "\"Oh, no, you're not!\"
					cries a voice from over the fence.
					\"You don't live here any more.
					I've found a new lover and filed for divorce.
					By island law, all I need do is walk three times
					in a circle around our bed.
					I've done that many times over the past few years!\""
				)
				(= seconds (PrintKalalau))
			)

			(readMsg3
				(Print "(You are stunned!
					You had no idea anything was wrong!)"
					LOW #dispose #time 11)
				(delay 14)
			)

			(insert3
				(Format @string "\"What do you mean, `divorce?' 
					`Walk three times?'
					Oh, Kalalau: say it isn't so!\"" )
				(= seconds (Print216))
			)

			(readMsg4
				(Format @string "\"It IS so, Larry!
					I'm leaving you and keeping the house.
					Now, please go and leave us alone!\"" )
				(= seconds (PrintKalalau))
			)

			(readMsg5
				(Format @string "\"`Us?!' What do you mean, `us?'
					Are you in our hot tub with someone else?
					Who? Who is it, Kalalau?\"" )
				(= seconds (Print216))
			)

			(readMsg6
				(Format @string "\"I'm with Bobbi, my new lover,\"
					she responds hurtfully.
					\"Bobbi is able to meet my needs where you never were.
					You'll never bore me to sleep again, Larry Laffer!\"" )
				(= seconds (PrintKalalau))
			)

			(readMsg7
				(Format @string "\"Kalalau!\" you cry.
					\"How could you possibly leave me for another man?\"" )
				(= seconds (Print216))
			)

			(readMsg8
				(Format @string "\"I didn't, you fool!\" she replies.
					\"Bobbi's a woman!\"" )
				(= seconds (PrintKalalau))
			)

			(readMsg9
				(SetFlg beenIn216)
				(Print (Format @string  "Poor Larry.
					Not only does Kalalau no longer love you,
					she's fallen in love with another woman!
					What could Kalalua possibly see in an
					Amazonian Harley-riding former-cannibal%s 
					slot-machine repairwoman?" 
						(if (>= filthLevel DIRTY) { lesbian} else {}))
						#dispose
						#time 28)
				(delay 31)
			)

			(plotDone
				(HandsOn)
				(cls)
				(delay 0)
			)


			;**		This section handles Larry walking to the mailbox and
			;**	opening it.

			(openBox
				(HandsOff)
				(ego
					ignoreActors,
					illegalBits		0,
					setMotion		MoveTo EGO_AT_BOX self,
				)
			)

			(openDelay
				(ego
					loop				facingNorth,
				)
				(delay 2)
			)

			(openBox3
				(ego
					view				vLroom,
					setLoop			lLopeningMailbox,
					cel				0,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
				(soundFX
					number			sMailboxOpening,
					loop				1,
					play,
				)
			)

			(lowerYourArm
				(aMailBox
					setCel 			(if (InRoom iCreditCard) cFull else cEmpty),
					stopUpd,
				)
				(ego
					setCycle			BegLoop self,
				)
			)

			(boxIsOpen
				(NormalEgo			facingNorth)
				(= boxOpen TRUE)
				(if (cast contains aKandBB)
					(soundFX
						number		sBubbles,
						loop			FOREVER,
						play,
					)
				)
			)


			;**		This section handles Larry closing his box
			;**	(even if he's not close to his box).

			(closeBox
				(HandsOff)
				(ego
					ignoreActors,
					illegalBits		0,
					setMotion		MoveTo EGO_AT_BOX self,
				)
			)

			(closeDelay
				(ego
					loop				facingNorth,
				)
				(delay 2)
			)

			(closeBox3
				(ego
					view				vLroom,
					setLoop			lLopeningMailbox,
					cel				0,
					cycleSpeed		1,
					setCycle			EndLoop self,
				)
			)

			(shutUpYourBox
				(aMailBox
					setCel 			cBoxClosed,
					stopUpd,
				)
				(ego
					setCycle			BegLoop self,
				)
				(soundFX
					number			sMailboxClosing,
					loop				1,
					play,
				)
			)

			(boxIsClosed
				(NormalEgo			facingNorth)
				(= boxOpen FALSE)
				(if (cast contains aKandBB)
					(soundFX
						number		sBubbles,
						loop			FOREVER,
						play,
					)
				)
			)


			;**	Return to the box and get the card from an open box.

			(getEnvelope
				(HandsOff)
				(ego
					ignoreActors,
					illegalBits		0,
					setMotion		MoveTo EGO_AT_BOX self,
				)
			)

			(envelopeDelay
				(ego
					loop				facingNorth,
				)
				(delay 2)
			)

			(reachIntoBox
				(ego 
					cycleSpeed		1,
					view				vLroom,
					setLoop			lLopeningMailbox,
					cel				0,
					setCycle			EndLoop self,
				)
			)
			(getEnvelope3
				(aMailBox
					setCel			cEmpty,
					stopUpd,
				)
				(ego
					setLoop			lLgettingMail,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(holdUp
				(delay 2)
			)

			(eagerly
				(Print "Eagerly, you take the envelope from the mailbox.
					Could this be that big break from Ed McMahon you've
					been hoping for?" BIG)
				(= cycles 22)
			)

			(raiseEnvelope
				(ego
					view				vLroom,
					setLoop			lLholdingUpMail,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(raiseEnvelope2
				(= cycles 20)
			)


			(putEnvelopeDown
				(Print "Say! It's an answer from that credit card company
					whose application you mailed only 15 weeks ago." BIG)
				(ego
					setCycle			BegLoop self,
				)
			)

			(downDelay
				(ego
					setLoop			lLgettingMail,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(envelopeIsOpen
				(ego get iCreditCard)
				(theGame changeScore 20)
				(Print "Ripping opening the envelope,
					you discover your new credit card.
					And, look! It's in your name only!
					Kalalau can't claim this is community property!"
					#icon vLroom lMailIcon 0
				 	HIGH
				)
				(delay 3)
			)

			(allDone
				(NormalEgo facingNorth)
			)
		)
	)
)

(instance aKandBB of Prop
	(properties
		view				vK&BBinHotTub
		loop				lKtalking
	)

	(method (init)
		(super init)
		(self
			posn			246 42,
			setScript	KandBBScript,
		)
	)
)


(instance KandBBScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(if (Said 'talk/girl, woman, kalalau')
			(switch (++ girlTalkCount)
				(1
					(Print "\"Oh, Kalalau,\" you cry,
						\"is there anything I can do to win you back?\"")
					(Print "There is no reply.")
				)
				(2
					(Print "\"Kalalau, you can't treat me like dirt, you know,\"
						you say, growing indignant.
						\"I don't have to stand here taking abuse from you.
						I can do that anywhere!\"")
					(Print "There is no reply.")
				)
				(3
					(Print "\"All right, that's it!
						You may refuse to love me, but you won't ignore me.
						I'm leaving you -- for good!\"")
					(Print "At last there is a response from over the wall...")
					(Print "\"Good!\"")
				)
				(else
					(Print "\"Knock, knock,\" you shout.")
					(Print "\"Who's there?\" replies Kalalau.")
					(Print "\"Not me, baby! I'm outta here!!\"")
				)
			)
		)

		(if (Said '/spa, tub')
			(Print "They seem to be enjoying themselves.")
		)

		(if (Said 'look/girl, woman, kalalau')
			(if (> filthLevel DIRTY)
				(Print "It seems Kalalau is too busy playing \"little Dutch boy\"
					to worry about you!")
			else
				(Print "They can't see you over the fence,
					they can't hear you over the roar of the bubbles,
					and they don't want to see or hear you ever again!")
			)
		)
	)

	(method (changeState newState)		;**	 &tmp KandBBX KandBBY [str 100]
		(switch (= state newState)

			(KandBBinit
				(delay 3)
			)

			(makeKtalk
				(aKandBB
					loop			lKtalking,
					setCycle	Forward
				)
				(= cycles (Random 20 40))
			)

			(stopKtalk
				(aKandBB
					setCel		3
				)
				(= cycles (Random 20 40))
			)

			(makeBBtalk
				(aKandBB
					loop			lBBtalking,
					setCycle		Forward
				)
				(= cycles (Random 20 40))
			)

			(stopBBtalk
				(aKandBB
					setCel		3
				)
				(= cycles (Random 20 40))
			)

			(makeBBdive
				(if (== 1 (Random 1 4))
					(aKandBB
						loop		lMuffDiving,
						setCycle	EndLoop
					)
					(= cycles (Random 80 140))
				else
					(self changeState makeKtalk)
				)
			)

			(riseForAir
				(aKandBB
					setCycle		BegLoop
				)
				(self changeState makeKtalk)
			)
		)
	)
)


(instance blockFence2 of Block
	(properties
		top				190
		bottom			333
		left				0
		right				190
	)
)
(instance blockFence1 of Block
	(properties
		top				200
		bottom			333
		left				0
		right				333
	)
)

(procedure (PrintKalalau &tmp t )
	(Print @string
		#at 			10 15
		#title		{Kalalau says...}
		#width 		160
		#time			(= t (PrintDelay @string))
		#dispose
	)
	(return (+ 3 t))
)
(procedure (Print216 &tmp t )
	(Print @string
		#at 			10 123
		#title		{You say...}
		#width 		160
		#time			(= t (PrintDelay @string))
		#dispose
	)
	(return (+ 3 t))
)

(instance aCredit1 of Prop
	(properties
		view					vProgrammedBy
;		loop					lProgrammedBy
;		cel					0
		x						288
		y						131
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
		)
	)
)
(instance aCredit2 of Prop
	(properties
		view					vProgrammedBy
		loop					lAl
;		cel					0
		x						288
		y						154
		cycleSpeed			1
	)

	(method (init)
		(super init)
		(self
			setPri			15,
			ignoreActors,
			setScript		CreditsScript,
		)
	)
)

(instance CreditsScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(doCredits
				(delay 4)
			)

			(doCredits1
				(aCredit1 setCycle EndLoop)
				(= cycles 16)
			)

			(doCredits2
				(aCredit2 setCycle EndLoop)
				(= cycles 22)
			)

			(doCredits3
				(aCredit2 setCycle BegLoop self)
			)

			(doCredits4
				(aCredit2
					setLoop		lCarlos,
					setCycle 	EndLoop ,
				)
				(= cycles 22)
			)

			(doCredits5
				(SetFlg sawCredits216)
				(aCredit1 setCycle BegLoop)
				(aCredit2 setCycle BegLoop self)
			)

			(clearCredits
				(aCredit1 dispose)
				(aCredit2 dispose)
			)
		)
	)
)

(instance aMailBox of Prop
	(properties
		view				vRoom
		loop				lMailBox
		cel				cBoxClosed
 		x					BOX_X
		y					BOX_Y
		signal			stopUpdOn
	)
)

